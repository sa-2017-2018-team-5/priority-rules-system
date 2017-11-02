package fr.polytech.al.five.actions;

import fr.polytech.al.five.behaviour.TrafficLightState;
import fr.polytech.al.five.messages.RoutePlannedMessage;
import org.apache.log4j.Logger;

import java.util.function.Consumer;

/**
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
public class OnRoutePlanned {

    private static final Logger LOGGER = Logger.getLogger(OnRoutePlanned.class);

    private TrafficLightState trafficLightState;

    public OnRoutePlanned(TrafficLightState trafficLightState) {
        this.trafficLightState = trafficLightState;
    }

    public Consumer<RoutePlannedMessage> getAction() {
        return message -> {
            LOGGER.info("Received a new route...");
            if (message.getEncounteredTrafficLights().contains(trafficLightState.getId())) {
                trafficLightState.waitCar(message.getCarId());
                LOGGER.info("Waiting a new car: " + message.getCarId());
            }
        };
    }
}
