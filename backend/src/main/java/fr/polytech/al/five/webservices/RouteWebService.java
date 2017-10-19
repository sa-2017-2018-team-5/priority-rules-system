package fr.polytech.al.five.webservices;

import fr.polytech.al.five.entities.Car;
import fr.polytech.al.five.entities.Position;
import fr.polytech.al.five.entities.Route;
import fr.polytech.al.five.exceptions.NotAuthorizedCarException;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.Optional;

@WebService(targetNamespace = "http://www.polytech.fr/al/five/route")
public interface RouteWebService {

    @WebMethod
    Route getRoute(Car car, Position destination)
            throws NotAuthorizedCarException;
}
