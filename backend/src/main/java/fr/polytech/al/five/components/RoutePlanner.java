package fr.polytech.al.five.components;

import fr.polytech.al.five.RouteBuilder;
import fr.polytech.al.five.TrafficSupervisor;
import fr.polytech.al.five.entities.Position;
import fr.polytech.al.five.entities.Route;
import fr.polytech.al.five.entities.TrafficInformation;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Stateless
public class RoutePlanner implements RouteBuilder {

    @EJB private TrafficSupervisor trafficSupervisor;

    @Override
    public Route getRoute(Position from, Position to, Date departureDate) {
        TrafficInformation information = trafficSupervisor.getTraffic();

        List<String> instructions = new ArrayList<>();
        instructions.add("Tournez à gauche.");
        instructions.add("Tournez tout droit.");
        instructions.add("GG, faites demi-tour.");
        instructions.add("Tu es arrivé à destination.");

        return new Route(0, instructions, new ArrayList<>(), new Date());
    }
}
