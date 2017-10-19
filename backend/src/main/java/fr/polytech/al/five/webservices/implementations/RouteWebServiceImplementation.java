package fr.polytech.al.five.webservices.implementations;

import fr.polytech.al.five.PriorityReader;
import fr.polytech.al.five.RouteBuilder;
import fr.polytech.al.five.entities.Car;
import fr.polytech.al.five.entities.Position;
import fr.polytech.al.five.entities.Route;
import fr.polytech.al.five.exceptions.NotAuthorizedCarException;
import fr.polytech.al.five.webservices.RouteWebService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@WebService(targetNamespace = "http://www.polytech.fr/al/five/route")
@Stateless(name = "RouteWS")
public class RouteWebServiceImplementation implements RouteWebService {

    @EJB private RouteBuilder routeBuilder;
    @EJB private PriorityReader priorityReader;

    @Override
    public Route getRoute(Car car, Position destination)
            throws NotAuthorizedCarException {
        Optional<Route> optionalRoute = priorityReader
                .getPriority(car.getType())
                .map(carType -> routeBuilder.getRoute(
                        car.getCurrentPosition(),
                        destination,
                        new Date()));

        if (optionalRoute.isPresent()) {
            return optionalRoute.get();
        } else {
            throw new NotAuthorizedCarException();
        }
    }
}
