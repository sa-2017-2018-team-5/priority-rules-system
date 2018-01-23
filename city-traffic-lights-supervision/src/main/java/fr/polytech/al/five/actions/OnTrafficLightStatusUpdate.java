package fr.polytech.al.five.actions;

import fr.polytech.al.five.behaviour.SupervisionState;
import fr.polytech.al.five.messages.TrafficLightStatusMessage;
import fr.polytech.al.five.messages.contents.LightStatus;
import org.apache.log4j.Logger;

import java.util.function.Consumer;

public class OnTrafficLightStatusUpdate {

    private static final Logger LOGGER = Logger.getLogger(OnTrafficLightStatusUpdate.class);
    private final SupervisionState state;

    public OnTrafficLightStatusUpdate(SupervisionState state) {
        this.state = state;
    }

    public Consumer<TrafficLightStatusMessage> getAction() {
        return message -> {
            int id = message.getTrafficLightId();
            LightStatus status = message.getLightStatus();

            state.updateTrafficLight(id, status);

            LOGGER.info("State of traffic light #" + id + " changed (" + status + "). Current state of the city:\n" + state);
        };
    }
}
