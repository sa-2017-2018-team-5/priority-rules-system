package fr.polytech.al.five.actions;

import fr.polytech.al.five.behaviour.CarQuery;
import fr.polytech.al.five.behaviour.TrafficLightsGroupState;
import fr.polytech.al.five.bus.BusChannel;
import fr.polytech.al.five.bus.BusInformation;
import fr.polytech.al.five.bus.PubSubEmitter;
import fr.polytech.al.five.messages.TrafficLightObservationMessage;
import fr.polytech.al.five.messages.TrafficLightOrdersMessage;
import fr.polytech.al.five.messages.contents.CarAction;
import fr.polytech.al.five.messages.contents.LightStatus;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

/**
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
public class OnTrafficLightObservation {

    private static final Logger LOGGER = Logger.getLogger(OnTrafficLightObservation.class);

    private PubSubEmitter pubSubEmitter;
    private TrafficLightsGroupState state;

    public OnTrafficLightObservation(BusInformation busInformation,
                                     TrafficLightsGroupState state) {
        this.state = state;
        pubSubEmitter = new PubSubEmitter(busInformation);
    }

    public Consumer<TrafficLightObservationMessage> getAction() {
        return message -> {
            if (state.knowsTrafficLight(message.getTrafficLightId())) {
                CarAction receivedAction = message.getCarAction();

                if (receivedAction == CarAction.SEEN) {
                    handleSeenCar(message.getTrafficLightId(), message.getCarId());
                } else if (receivedAction == CarAction.PASSED) {

                    handlePassedCar(message.getTrafficLightId(), message.getCarId());
                }
            }
        };
    }

    private void sendColorChange(int trafficLight, int car) {
        TrafficLightOrdersMessage message = new TrafficLightOrdersMessage(
                state.mustBecomeRed(trafficLight),
                state.mustBecomeGreen(trafficLight),
                LightStatus.FORCED);

        LOGGER.info("New order:");
        LOGGER.info("- " + message.getMustBecomeGreen() + " will become green");
        LOGGER.info("- " + message.getMustBecomeRed() + "will become red");

        try {
            pubSubEmitter.send(message, BusChannel.TRAFFIC_LIGHTS_ORDER);
            state.registerSeenCar(trafficLight, car);
        } catch (IOException | TimeoutException e) {
            LOGGER.error("Exception occurred while sending a message to the bus: " + e);
        }
    }

    private void handleSeenCar(int trafficLight, int carId) {
        LOGGER.info("Car #" + carId + " seen by traffic light #" + trafficLight);

        if (!state.isWaitingCar(carId)) {
            LOGGER.info("Was not waiting car #" + carId);
            return;
        }

        if (state.isBusyIntersection(trafficLight)) {
            LOGGER.info("Busy intersection for car #" + carId + " on traffic light #" + trafficLight);
            LOGGER.info("Adding car #" + carId + " request to traffic light #" + trafficLight + " queue");
            state.addPendingRequest(carId, state.getCar(carId).getPriority(), trafficLight);
        } else {
            LOGGER.info("Intersection not busy for car #" + carId + " on traffic light #" + trafficLight);
            LOGGER.info("Executing color change for car #" + carId + " on traffic light #" + trafficLight);
            sendColorChange(trafficLight, carId);
        }
    }

    private void handlePassedCar(int trafficLight, int carId) {
        LOGGER.info("Car #" + carId + " passed traffic light #" + trafficLight);


        if (state.isCarWaitingAt(trafficLight)) {
            CarQuery query = state.removePendingRequest(trafficLight);

            LOGGER.info("Treating car #" + query.getCarId() + " pending request");
            sendColorChange(query.getTrafficLightId(), query.getCarId());
            state.removeCarInfluence(carId);
            return;
        }
        LOGGER.info("No pending requests for traffic light #" + trafficLight);
        TrafficLightOrdersMessage message = new TrafficLightOrdersMessage(
                state.mustBecomeRed(trafficLight),
                state.mustBecomeGreen(trafficLight),
                LightStatus.NORMAL);

        List<Integer> resuming = new ArrayList<>();
        resuming.addAll(message.getMustBecomeGreen());
        resuming.addAll(message.getMustBecomeRed());

        LOGGER.info("New order:");
        LOGGER.info("- " + resuming + " will resume to their pattern.");

        try {
            pubSubEmitter.send(message, BusChannel.TRAFFIC_LIGHTS_ORDER);
            state.removeCarInfluence(carId);
        } catch (IOException | TimeoutException e) {
            LOGGER.error("Exception occurred while sending a message to the bus: " + e);
        }
    }
}
