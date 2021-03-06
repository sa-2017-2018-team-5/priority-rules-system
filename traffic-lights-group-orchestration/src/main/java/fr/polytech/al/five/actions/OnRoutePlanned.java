package fr.polytech.al.five.actions;

import fr.polytech.al.five.behaviour.KnownCar;
import fr.polytech.al.five.behaviour.TrafficLightsGroupState;
import fr.polytech.al.five.messages.RoutePlannedMessage;
import org.apache.log4j.Logger;

import java.util.function.Consumer;

/**
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
public class OnRoutePlanned {

    private static final Logger LOGGER = Logger.getLogger(OnRoutePlanned.class);

    private final TrafficLightsGroupState state;

    public OnRoutePlanned(TrafficLightsGroupState state) {
        this.state = state;
    }

    public Consumer<RoutePlannedMessage> getAction() {
        return message -> {
            LOGGER.info("Received a new route!");
            LOGGER.info("The car is " + message.getCarId() + " and it will encounter " + message.getEncounteredTrafficLights());

            KnownCar car = new KnownCar(message.getCarId(),
                    message.getCarPriority(), message.isEmergency());

            state.acknowledgeRoute(car, message.getEncounteredTrafficLights());
        };
    }
}
