package fr.polytech.al.five.actions;

import fr.polytech.al.five.behaviour.TrafficLightState;
import fr.polytech.al.five.bus.BusChannel;
import fr.polytech.al.five.bus.BusInformation;
import fr.polytech.al.five.bus.PubSubEmitter;
import fr.polytech.al.five.messages.Message;
import fr.polytech.al.five.messages.TrafficLightOrdersMessage;
import fr.polytech.al.five.messages.TrafficLightStatusMessage;
import fr.polytech.al.five.messages.contents.LightStatus;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

public class OnOrchestrationOrder {
    private static final Logger LOGGER = Logger.getLogger(OnOrchestrationOrder.class);

    private TrafficLightState state;
    private PubSubEmitter pubSubEmitter;

    public OnOrchestrationOrder(BusInformation busInformation,
                                TrafficLightState state) {
        this.state = state;
        pubSubEmitter = new PubSubEmitter(busInformation);
    }

    public Consumer<TrafficLightOrdersMessage> getAction() {
        return message -> {
            LOGGER.info("Received a car status update");

            List<Integer> mustBecomeGreen = message.getMustBecomeGreen();
            List<Integer> mustBecomeRed = message.getMustBecomeRed();

            // The orchestration orders that all concerned traffic light resume to their normal pattern.
            if (message.getTrafficLightStatus().equals(LightStatus.NORMAL)) {
                whenResumeToNormal(mustBecomeGreen, mustBecomeRed);
                return;
            }

            // The orchestration orders the traffic light to be forced to green.
            if (mustBecomeGreen.contains(state.getId())) {
                whenInForcedToGreen(mustBecomeRed);

                return;
            }

            // The orchestration orders the traffic light to be forced to red.
            if (mustBecomeRed.contains(state.getId())) {
                forceToRed();
            }
        };
    }

    private void whenResumeToNormal(List<Integer> mustBecomeGreen, List<Integer> mustBecomeRed) {
        resumeNormalPattern(mustBecomeGreen);
        resumeNormalPattern(mustBecomeRed);
    }

    private void whenInForcedToGreen(List<Integer> mustBecomeRed) {
        if (mustBecomeRed.isEmpty()) {
            forceToGreen();
        } else {
            handleTrafficLightToWait(state.getTrafficLightStatus(), mustBecomeRed);

            if (state.isTrafficLightReadyToTurnGreen()) {
                forceToGreen();
            } else {
                waitingToGreen();
            }
        }
    }

    private void waitingToGreen() {
        state.setTrafficLightStatus(LightStatus.WAITING_TO_TURN_GREEN);
        LOGGER.info("Waiting other traffic lights before turning to green.");
    }

    private void forceToGreen() {
        state.setTrafficLightStatus(LightStatus.FORCED_GREEN);
        LOGGER.info("Forced to green.");
    }

    private void forceToRed() {
        LOGGER.info("Forcing to red...");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            LOGGER.error("Error while sleeping: " + e);
        }

        state.setTrafficLightStatus(LightStatus.FORCED_RED);
        LOGGER.info("Forced to red.");
        handleLightStatusChange(state.getId(), state.getTrafficLightStatus());
    }

    private void handleTrafficLightToWait(LightStatus trafficLightStatus, List<Integer> trafficLightIds) {
        // Case the light is already waiting to turn green or already green
        if (trafficLightStatus.equals(LightStatus.WAITING_TO_TURN_GREEN)
                || trafficLightStatus.equals(LightStatus.FORCED_GREEN)) {
            return;
        }

        for (int id : trafficLightIds) {
            if (!state.isWaitingForcedRedLight(id)) {
                state.waitTrafficLight(id);
            }
        }
    }

    private void handleLightStatusChange(int trafficLightId, LightStatus newStatus) {
        Message message = new TrafficLightStatusMessage(trafficLightId, newStatus);

        try {
            pubSubEmitter.send(message, BusChannel.TRAFFIC_LIGHT_STATUS);
        } catch (IOException | TimeoutException e) {
            LOGGER.error("Exception occurred while sending a message to the bus: " + e);
        }
    }

    private void resumeNormalPattern(List<Integer> trafficLightIds) {
        if (trafficLightIds.contains(state.getId())) {
            state.setTrafficLightStatus(LightStatus.NORMAL);

            LOGGER.info("Resumed to normal state.");
        }
    }
}