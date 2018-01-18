package fr.polytech.al.five.actions;

import fr.polytech.al.five.behaviour.TrafficLightState;
import fr.polytech.al.five.messages.TrafficLightStatusMessage;
import fr.polytech.al.five.messages.contents.LightStatus;
import org.apache.log4j.Logger;

import java.util.function.Consumer;

/**
 * When received a message which indicates the status of another Traffic Light in the city.
 */
public class OnTrafficLightStatusUpdate {

    private static final Logger LOGGER = Logger.getLogger(OnTrafficLightStatusUpdate.class);

    private TrafficLightState trafficLightState;

    public OnTrafficLightStatusUpdate(TrafficLightState trafficLightState) {
        this.trafficLightState = trafficLightState;
    }

    public Consumer<TrafficLightStatusMessage> getAction() {
        return message -> {
            LOGGER.info("Received a traffic light status update");

            // When the other traffic light was waited and is now forced to red...
            if (message.getLightStatus().equals(LightStatus.FORCED_RED) &&
                    trafficLightState.isWaitingForcedRedLight(message.getTrafficLightId())) {
                trafficLightState.stopWaitingTrafficLight(message.getTrafficLightId());

                LOGGER.info("Acknowledge traffic light #" + message.getTrafficLightId() + " status.");

                if (trafficLightState.isTrafficLightReadyToTurnGreen()) {
                    trafficLightState.setTrafficLightStatus(LightStatus.FORCED_GREEN);

                    LOGGER.info("Forced to green.");
                }
            }
        };
    }
}
