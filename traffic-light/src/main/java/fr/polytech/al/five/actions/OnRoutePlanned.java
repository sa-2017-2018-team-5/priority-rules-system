package fr.polytech.al.five.actions;

import fr.polytech.al.five.behaviour.TrafficLightState;
import fr.polytech.al.five.messages.RoutePlannedMessage;

import java.util.function.Consumer;

/**
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
public class OnRoutePlanned {

    private TrafficLightState trafficLightState;

    public OnRoutePlanned(TrafficLightState trafficLightState) {
        this.trafficLightState = trafficLightState;
    }

    public Consumer<RoutePlannedMessage> getAction() {
        return message -> {
            if (message.getEncounteredTrafficLights().contains(trafficLightState.getId())) {
                trafficLightState.waitCar(message.getCarId());
            }
        };
    }
}
