package fr.polytech.al.five.webservices.implementations;

import fr.polytech.al.five.PriorityReader;
import fr.polytech.al.five.RouteBuilder;
import fr.polytech.al.five.RouteRegisterer;
import fr.polytech.al.five.entities.Car;
import fr.polytech.al.five.entities.Position;
import fr.polytech.al.five.entities.Route;
import fr.polytech.al.five.exceptions.NotAuthorizedCarException;
import fr.polytech.al.five.webservices.RouteWebService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jms.JMSException;
import javax.jws.WebService;
import java.util.Date;
import java.util.Optional;

/**
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
@WebService(targetNamespace = "http://www.polytech.fr/al/five/route")
@Stateless(name = "RouteWS")
public class RouteWebServiceImplementation implements RouteWebService {

    @EJB private RouteBuilder routeBuilder;
    @EJB private PriorityReader priorityReader;
    @EJB private RouteRegisterer routeRegisterer;

    @Override
    public Route getRoute(Car car, Position destination)
            throws NotAuthorizedCarException {
        Optional<Route> optionalRoute = priorityReader
                .getPriority(car.getType().getName())
                .map(carType -> routeBuilder.getRoute(
                        car.getCurrentPosition(),
                        destination,
                        new Date()));

        if (optionalRoute.isPresent()) {
            Route route = optionalRoute.get();
            try {
                routeRegisterer.sendRoute(car, route);
            } catch (JMSException e) {
                // TODO: Add logger instead of print.
                System.out.println(e.getMessage());
            }
            return route;
        } else {
            throw new NotAuthorizedCarException();
        }
    }
}
