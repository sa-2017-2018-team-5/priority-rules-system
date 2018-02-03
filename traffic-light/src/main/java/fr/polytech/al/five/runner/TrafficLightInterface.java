package fr.polytech.al.five.runner;

import asg.cliche.Command;
import fr.polytech.al.five.behaviour.TrafficLightState;
import fr.polytech.al.five.bus.BusChannel;
import fr.polytech.al.five.bus.TaskEmitter;
import fr.polytech.al.five.messages.Message;
import fr.polytech.al.five.messages.TrafficLightObservationMessage;
import fr.polytech.al.five.messages.contents.CarAction;
import fr.polytech.al.five.sabotage.SabotagedAction;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TrafficLightInterface {

    private static final Logger LOGGER = Logger.getLogger(TrafficLightInterface.class);

    private final TaskEmitter taskEmitter;
    private final TrafficLightState trafficLightState;

    public TrafficLightInterface(TaskEmitter taskEmitter, TrafficLightState trafficLightState) {
        this.trafficLightState = trafficLightState;
        this.taskEmitter = taskEmitter;
    }

    @Command(name = "car-seen")
    public void carSeen(Integer carId) {
        Message message = new TrafficLightObservationMessage(trafficLightState.getId(), carId, CarAction.SEEN);

        sendObservation(message);
    }

    @Command(name = "car-passed")
    public void carPassed(Integer carId) {
        Message message = new TrafficLightObservationMessage(trafficLightState.getId(), carId, CarAction.PASSED);

        sendObservation(message);
    }

    private void sendObservation(Message message) {
        try {
            taskEmitter.send(message, BusChannel.TRAFFIC_LIGHT_OBSERVATION_TASK);
        } catch (IOException e) {
            LOGGER.error("Output exception while sending a message to the bus: " + e);
        } catch (TimeoutException e) {
            LOGGER.error("Timeout exception while sending a message to the bus: " + e);
        }
    }

    private void sendSabotagedObservation(Message message) {
        SabotagedAction action = new SabotagedAction(() -> {
            try {
                taskEmitter.send(message, BusChannel.TRAFFIC_LIGHT_OBSERVATION_TASK);
            } catch (IOException e) {
                LOGGER.error("Output exception while sending a message to the bus: " + e);
            } catch (TimeoutException e) {
                LOGGER.error("Timeout exception while sending a message to the bus: " + e);
            }
        });

        action.run();
    }

    @Command
    public void status() {
        LOGGER.info("Current status: " + trafficLightState.getTrafficLightStatus());
    }

}
