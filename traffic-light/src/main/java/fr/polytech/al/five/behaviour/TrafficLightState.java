package fr.polytech.al.five.behaviour;

import fr.polytech.al.five.bus.BusChannel;
import fr.polytech.al.five.bus.MessageEmitter;
import fr.polytech.al.five.messages.Message;
import fr.polytech.al.five.messages.TrafficLightStatusMessage;
import fr.polytech.al.five.messages.contents.LightStatus;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeoutException;

/**
 * The state of the traffic light. For now, this is stateful but we may make it
 * stateless in a future release.
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
public class TrafficLightState {

    private static final Logger LOGGER = Logger.getLogger(TrafficLightState.class);

    private final int trafficLightId;
    private LightStatus trafficLightStatus;
    private Set<Integer> expectedForcedRedLights;
    private final MessageEmitter messageEmitter;

    public TrafficLightState(int trafficLightId, MessageEmitter messageEmitter) {
        this.trafficLightId = trafficLightId;
        expectedForcedRedLights = new HashSet<>();
        trafficLightStatus = LightStatus.NORMAL;
        this.messageEmitter = messageEmitter;
    }

    public int getId() {
        return trafficLightId;
    }

    public LightStatus getTrafficLightStatus() {
        return trafficLightStatus;
    }

    public void setTrafficLightStatus(LightStatus status) {
        this.trafficLightStatus = status;

        Message message = new TrafficLightStatusMessage(getId(), getTrafficLightStatus());

        try {
            messageEmitter.send(message, BusChannel.SUPERVISION);
        } catch (IOException e) {
            LOGGER.error("Could not update the supervision.");
        } catch (TimeoutException e) {
            LOGGER.error("Could not connect to the bus channel.");
        }
    }

    public void waitTrafficLight(int trafficLightId) {
        expectedForcedRedLights.add(trafficLightId);
    }

    public void stopWaitingTrafficLight(int trafficLightId) {
        expectedForcedRedLights.remove(trafficLightId);
    }

    public boolean isWaitingForcedRedLight(int trafficLightId) {
        return expectedForcedRedLights.contains(trafficLightId);
    }

    public boolean isTrafficLightReadyToTurnGreen() {
        return expectedForcedRedLights.isEmpty() &&
                trafficLightStatus.equals(LightStatus.WAITING_TO_TURN_GREEN);
    }
}
