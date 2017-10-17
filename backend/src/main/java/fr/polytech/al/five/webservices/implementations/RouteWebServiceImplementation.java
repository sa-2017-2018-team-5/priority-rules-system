package fr.polytech.al.five.webservices.implementations;

import fr.polytech.al.five.entities.Car;
import fr.polytech.al.five.entities.Position;
import fr.polytech.al.five.entities.Route;
import fr.polytech.al.five.webservices.RouteWebService;
import org.apache.commons.lang.NotImplementedException;

import javax.ejb.Stateless;
import javax.jws.WebService;

@WebService(targetNamespace = "http://www.polytech.fr/al/five/route")
@Stateless(name = "RouteWS")
public class RouteWebServiceImplementation implements RouteWebService {

    @Override
    public Route getRoute(Car car, Position destination) {
        throw new NotImplementedException();
    }
}
