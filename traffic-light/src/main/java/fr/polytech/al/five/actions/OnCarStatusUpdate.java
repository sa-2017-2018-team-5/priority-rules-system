package fr.polytech.al.five.actions;

import fr.polytech.al.five.behaviour.TrafficLightState;
import fr.polytech.al.five.bus.BusChannel;
import fr.polytech.al.five.bus.BusInformation;
import fr.polytech.al.five.bus.MessageEmitter;
import fr.polytech.al.five.messages.Message;
import fr.polytech.al.five.messages.TrafficLightOrdersMessage;
import fr.polytech.al.five.messages.TrafficLightStatusMessage;
import fr.polytech.al.five.messages.contents.LightStatus;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

public class OnCarStatusUpdate {
    private static final Logger LOGGER = Logger.getLogger(OnCarStatusUpdate.class);

    private TrafficLightState trafficLightState;
    private MessageEmitter messageEmitter;

    public OnCarStatusUpdate(BusInformation busInformation,
                             TrafficLightState trafficLightState) {
        this.trafficLightState = trafficLightState;
        messageEmitter = new MessageEmitter(busInformation);
    }

    public Consumer<TrafficLightOrdersMessage> getAction() {
        return message -> {
            LOGGER.info("Received a car status update");

            if (message.getTrafficLightStatus().equals(LightStatus.NORMAL)) {
                // First return green light to normal pattern then red light.
                // So that we don't have all the light turning green all of a sudden.
                resumeNormalPattern(message.getMustBecomeGreen());
                resumeNormalPattern(message.getMustBecomeRed());
            } else if (message.getMustBecomeGreen().contains(trafficLightState.getId())
                    && !message.getMustBecomeRed().isEmpty()) {
                handleTrafficLightToWait(trafficLightState.getTrafficLightStatus(), message.getMustBecomeRed());
                // Case all the light are already red.
                if (trafficLightState.isTrafficLightReadyToTurnGreen()) {
                    trafficLightState.setTrafficLightStatus(LightStatus.FORCED_GREEN);
                    LOGGER.info("Forced to green.");
                } else {
                    trafficLightState.setTrafficLightStatus(LightStatus.WAITING_TO_TURN_GREEN);
                    LOGGER.info("Waiting other traffic lights before turning to green.");
                }
            } else if (message.getMustBecomeRed().contains(trafficLightState.getId())) {
                LOGGER.info("Forcing to red...");

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    LOGGER.error("Error while sleeping: " + e);
                }

                trafficLightState.setTrafficLightStatus(LightStatus.FORCED_RED);
                LOGGER.info("Forced to red.");
                handleLightStatusChange(trafficLightState.getId(), trafficLightState.getTrafficLightStatus());
            }
        };
    }

    private void handleTrafficLightToWait(LightStatus trafficLightStatus, List<Integer> trafficLightIds) {
        // Case the light is already waiting to turn green or already green
        if (trafficLightStatus.equals(LightStatus.WAITING_TO_TURN_GREEN)
                || trafficLightStatus.equals(LightStatus.FORCED_GREEN)) {
            return;
        }

        for (int id : trafficLightIds) {
            if (!trafficLightState.isWaitingForcedRedLight(id)) {
                trafficLightState.waitTrafficLight(id);
            }
        }
    }

    private void handleLightStatusChange(int trafficLightId, LightStatus newStatus) {
        Message message = new TrafficLightStatusMessage(trafficLightId, newStatus);

        try {
            messageEmitter.send(message, BusChannel.TRAFFIC_LIGHT_STATUS);
        } catch (IOException | TimeoutException e) {
            LOGGER.error("Exception occurred while sending a message to the bus: " + e);
        }
    }

    private void resumeNormalPattern(List<Integer> trafficLightIds) {
        if (trafficLightIds.contains(trafficLightState.getId())) {
            trafficLightState.setTrafficLightStatus(LightStatus.NORMAL);

            LOGGER.info("Resumed to normal state.");
        }
    }
}