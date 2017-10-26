package fr.polytech.al.five.webservices;

import fr.polytech.al.five.entities.Car;
import fr.polytech.al.five.entities.Position;
import fr.polytech.al.five.entities.Route;
import fr.polytech.al.five.exceptions.NotAuthorizedCarException;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.Optional;

/**
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
@WebService(targetNamespace = "http://www.polytech.fr/al/five/route")
public interface RouteWebService {

    /**
     * Builds a route between the car position and its destination.
     * @param car A car with its position and type.
     * @param destination A place in the city.
     * @return An route from the car to the destination.
     * @throws NotAuthorizedCarException Is thrown when the car has no privilege.
     */
    @WebMethod
    Route getRoute(Car car, Position destination)
            throws NotAuthorizedCarException;
}
