package fr.polytech.al.five.actions;

import fr.polytech.al.five.behaviour.CarQuery;
import fr.polytech.al.five.behaviour.TrafficLightsGroupState;
import fr.polytech.al.five.bus.BusChannel;
import fr.polytech.al.five.bus.BusInformation;
import fr.polytech.al.five.bus.MessageEmitter;
import fr.polytech.al.five.messages.TrafficLightObservationMessage;
import fr.polytech.al.five.messages.TrafficLightOrdersMessage;
import fr.polytech.al.five.messages.contents.CarAction;
import fr.polytech.al.five.messages.contents.LightStatus;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

/**
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
public class OnTrafficLightObservation {

    private static final Logger LOGGER = Logger.getLogger(OnTrafficLightObservation.class);

    private MessageEmitter messageEmitter;
    private TrafficLightsGroupState state;

    public OnTrafficLightObservation(BusInformation busInformation,
                                     TrafficLightsGroupState state) {
        this.state = state;
        messageEmitter = new MessageEmitter(busInformation);
    }

    public Consumer<TrafficLightObservationMessage> getAction() {
        return message -> {
            if (state.knowsTrafficLight(message.getTrafficLightId())) {
                CarAction receivedAction = message.getCarAction();

                if (receivedAction == CarAction.SEEN) {
                    handleSeenCar(message.getTrafficLightId(), message.getCarId());
                } else if (receivedAction == CarAction.PASSED) {
                    handlePassedCar(message.getTrafficLightId(), message.getCarId());
                    //handlePassedCar2(message.getTrafficLightId(), message.getCarId());
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
            messageEmitter.send(message, BusChannel.TRAFFIC_LIGHTS_ORDER);
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
            //state.addQuery(trafficLight, carId);
            state.addPendingRequest(trafficLight, carId);
        } else {
            LOGGER.info("Intersection not busy for car #" + carId + " on traffic light #" + trafficLight);
            LOGGER.info("Executing color change for car #" + carId + " on traffic light #" + trafficLight);
            sendColorChange(trafficLight, carId);
        }
    }

    private void handlePassedCar(int trafficLight, int carId) {
        LOGGER.info("Car #" + carId + " passed traffic light #" + trafficLight);

        if (state.isPendingRequests(trafficLight)) {
            int nextPriorityCarId = state.removePendingRequest(trafficLight);

            LOGGER.info("Treating car #" + nextPriorityCarId + " pending request");
            sendColorChange(trafficLight, nextPriorityCarId);
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
            messageEmitter.send(message, BusChannel.TRAFFIC_LIGHTS_ORDER);
        } catch (IOException | TimeoutException e) {
            LOGGER.error("Exception occurred while sending a message to the bus: " + e);
        }
    }

    private void handlePassedCar2(int trafficLight, int carId) {
        LOGGER.trace("The car " + carId + " passed the traffic light " + trafficLight + ".");

        state.makeTrafficLightStopWaitCar(trafficLight, carId);

        if (!state.trafficLightIsWaiting(trafficLight)) {
            LOGGER.info("No more car waited on traffic light #" + trafficLight);
            Optional<CarQuery> nextQuery = state.nextQuery();

            if (nextQuery.isPresent()) {
                LOGGER.info("There is a query to be fulfilled.");
                handleSeenCar(nextQuery.get().getTrafficLightId(), nextQuery.get().getCar().getId());

                // Get all the associated queries.
                state.popQueriesForTrafficLight(nextQuery.get().getTrafficLightId()).forEach(query ->
                        state.makeTrafficLightWaitCar(query.getTrafficLightId(), query.getCar().getId()));
            } else {
                LOGGER.info("Nothing to do, resume all concerned traffic lights.");
                sendResume(trafficLight);
            }
        } else {
            LOGGER.info("There is still cars to pass on traffic light #" + trafficLight);
        }
    }

    private void sendResume(int trafficLight) {
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
            messageEmitter.send(message, BusChannel.TRAFFIC_LIGHTS_ORDER);
        } catch (IOException | TimeoutException e) {
            LOGGER.error("Exception occurred while sending a message to the bus: " + e);
        }
    }
}
