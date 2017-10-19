package fr.polytech.al.five.webservices.implementations;

import fr.polytech.al.five.entities.Car;
import fr.polytech.al.five.entities.Position;
import fr.polytech.al.five.entities.Route;
import fr.polytech.al.five.webservices.RouteWebService;
import org.apache.commons.lang.NotImplementedException;

import javax.ejb.Stateless;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebService(targetNamespace = "http://www.polytech.fr/al/five/route")
@Stateless(name = "RouteWS")
public class RouteWebServiceImplementation implements RouteWebService {

    @Override
    public Route getRoute(Car car, Position destination) {
        List<String> instructions = new ArrayList<>();
        instructions.add("Tournez à gauche.");
        instructions.add("Tournez tout droit.");
        instructions.add("GG, faites demi-tour.");
        instructions.add("Tu es arrivé à destination.");

        return new Route(0, instructions, new ArrayList<>(), new Date());
    }
}
