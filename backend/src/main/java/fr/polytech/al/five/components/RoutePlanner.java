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
    public Route getRoute(Integer id, Position from, Position to, Date departureDate) {
        TrafficInformation information = trafficSupervisor.getTraffic("1");
        LOGGER.trace(information);

        List<String> instructions = new ArrayList<>();
        List<TrafficLight> encounteredTrafficLights = new ArrayList<>();

        switch (id) {
            case 1: {
                instructions.add("Vous pouvez démarrer.");
                instructions.add("Continuez tout droit sur 500m.");
                instructions.add("Au feu tournez à gauche.");
                instructions.add("Vous êtes arrivé à destination.");
                encounteredTrafficLights.add(new TrafficLight(1, new Position(0f, 1f)));
                encounteredTrafficLights.add(new TrafficLight(3, new Position(0f, 0f)));
                break;
            }
            case 2: {
                instructions.add("Vous pouvez démarrer.");
                instructions.add("Au feu tournez à gauche.");
                instructions.add("Continuez tout droit sur 200m.");
                instructions.add("Au feu suivant tournez à gauche.");
                instructions.add("Vous êtes arrivé à destination.");
                encounteredTrafficLights.add(new TrafficLight(2, new Position(0f, 1f)));
                encounteredTrafficLights.add(new TrafficLight(3, new Position(0f, 0f)));
                break;
            }
            case 3: {
                instructions.add("Vous pouvez démarrer.");
                instructions.add("Continuez tout droit sur 500m.");
                instructions.add("Vous êtes arrivé à destination.");
                encounteredTrafficLights.add(new TrafficLight(4, new Position(0f, 0f)));
                break;
            }
            case 4: {
                instructions.add("Vous pouvez démarrer.");
                instructions.add("Au feu tournez à droite.");
                instructions.add("Continuez tout droit sur 200m.");
                instructions.add("Vous êtes arrivé à destination.");
                encounteredTrafficLights.add(new TrafficLight(5, new Position(0f, 0f)));
                break;
            }
        }

        return new Route(0, instructions, encounteredTrafficLights, new Date());
    }
}
