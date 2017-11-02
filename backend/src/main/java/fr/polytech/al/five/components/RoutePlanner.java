package fr.polytech.al.five.components;

import fr.polytech.al.five.RouteBuilder;
import fr.polytech.al.five.TrafficSupervisor;
import fr.polytech.al.five.entities.Position;
import fr.polytech.al.five.entities.Route;
import fr.polytech.al.five.entities.TrafficInformation;
import fr.polytech.al.five.entities.TrafficLight;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
@Stateless
public class RoutePlanner implements RouteBuilder {

    private static final Logger LOGGER = Logger.getLogger(RoutePlanner.class);

    @EJB private TrafficSupervisor trafficSupervisor;

    @Override
    public Route getRoute(Position from, Position to, Date departureDate) {
        TrafficInformation information = trafficSupervisor.getTraffic("1");
        LOGGER.trace(information);

        List<String> instructions = new ArrayList<>();
        instructions.add("Vous pouvez démarrer.");
        instructions.add("Tournez tout droit sur 50 mètres.");
        instructions.add("Arrêtez-vous au feu SVP.");
        instructions.add("Tournez à gauche au croisement.");
        instructions.add("Vous êtes arrivé à destination.");

        List<TrafficLight> encounteredTrafficLights = new ArrayList<>();
        encounteredTrafficLights.add(new TrafficLight(1, new Position(10f, 10f)));

        return new Route(0, instructions, encounteredTrafficLights, new Date());
    }
}
