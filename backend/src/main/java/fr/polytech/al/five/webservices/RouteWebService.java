package fr.polytech.al.five.webservices;

import fr.polytech.al.five.entities.Car;
import fr.polytech.al.five.entities.Position;
import fr.polytech.al.five.entities.Route;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(targetNamespace = "http://www.polytech.fr/al/five/route")
public interface RouteWebService {

    @WebMethod
    Route getRoute(Car car, Position destination);
}
