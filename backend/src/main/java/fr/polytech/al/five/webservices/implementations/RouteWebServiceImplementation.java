package fr.polytech.al.five.webservices.implementations;

import fr.polytech.al.five.PriorityReader;
import fr.polytech.al.five.RouteBuilder;
import fr.polytech.al.five.RouteRegisterer;
import fr.polytech.al.five.entities.Car;
import fr.polytech.al.five.entities.CarType;
import fr.polytech.al.five.entities.Position;
import fr.polytech.al.five.entities.Route;
import fr.polytech.al.five.exceptions.NotAuthorizedCar;
import fr.polytech.al.five.webservices.RouteWebService;
import org.apache.log4j.Logger;

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

    private static final Logger LOGGER = Logger.getLogger(RouteWebServiceImplementation.class);

    @EJB private RouteBuilder routeBuilder;
    @EJB private PriorityReader priorityReader;
    @EJB private RouteRegisterer routeRegisterer;

    @Override
    public Route getRoute(Car car, Position destination)
            throws NotAuthorizedCar {
        Optional<CarType> carType = priorityReader.getPriority(car.getType().getName());

        carType.ifPresent(car::setType);

        Optional<Route> optionalRoute = carType.map(type ->
                routeBuilder.getRoute(
                        car.getId(),
                        car.getCurrentPosition(),
                        destination,
                        new Date()));

        if (optionalRoute.isPresent()) {
            Route route = optionalRoute.get();
            
            try {
                routeRegisterer.sendRoute(car, route);
            } catch (JMSException e) {
                LOGGER.error("An error occurred while sending the route.", e);
            }

            return route;
        } else {
            LOGGER.info("The car #" + car.getId() + " is not authorized to use the route service.");
            throw new NotAuthorizedCar();
        }
    }
}
