package fr.polytech.al.five.actions;

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
        LOGGER.info("- " + message.getMustBecomeGreen() + " will become green.");
        LOGGER.info("- " + message.getMustBecomeRed() + "will become red.");

        try {
            messageEmitter.send(message, BusChannel.TRAFFIC_LIGHTS_ORDER);
            state.registerSeenCar(trafficLight, car);
        } catch (IOException | TimeoutException e) {
            LOGGER.error("Exception occurred while sending a message to the bus: " + e);
        }
    }

    private void handleSeenCar(int trafficLight, int carId) {
        LOGGER.info("car" + carId + " seen by the traffic light " + trafficLight + ".");

        if (!state.isWaitingCar(carId)) {
            return;
        }

        if (state.isBusyIntersection(trafficLight)) {
            LOGGER.info("The intersection is busy.");
            LOGGER.info("car" + carId + "'s request added to traffic-light" + trafficLight + "'s queue.");
            state.addPendingRequest(trafficLight, carId);
        } else {
            LOGGER.info("The intersection is not busy.");
            sendColorChange(trafficLight, carId);
        }
    }

    private void handlePassedCar(int trafficLight, int carId) {
        LOGGER.info("car" + carId + " passed traffic-light" + trafficLight + ".");

        if (state.isPendingRequests(trafficLight)) {
            int nextPriorityCarId = state.removePendingRequest(trafficLight);

            LOGGER.info("Treating car" + nextPriorityCarId + "'s pending request.");
            sendColorChange(trafficLight, nextPriorityCarId);
            return;
        }
        LOGGER.info("No pending requests for traffic-light" + trafficLight + ".");
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
