package fr.polytech.al.five.actions;

import fr.polytech.al.five.behaviour.TrafficLightsGroupState;
import fr.polytech.al.five.bus.BusChannel;
import fr.polytech.al.five.bus.BusInformation;
import fr.polytech.al.five.bus.MessageEmitter;
import fr.polytech.al.five.messages.Message;
import fr.polytech.al.five.messages.TrafficLightOrdersMessage;
import fr.polytech.al.five.messages.contents.CarAction;
import fr.polytech.al.five.messages.TrafficLightObservationMessage;
import org.apache.log4j.Logger;

import java.io.IOException;
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
                if (CarAction.SEEN.equals(message.getCarAction())) {
                    handleSeenCar(message.getTrafficLightId(), message.getCarId());
                } else {
                    // Supposed to be CarAction.PASSED.
                    handlePassedCar(message.getTrafficLightId(), message.getCarId());
                }
            }
        };
    }

    private void handleSeenCar(int trafficLight, int car) {
        Message message = new TrafficLightOrdersMessage(
                state.mustBecomeRed(trafficLight),
                state.mustBecomeGreen(trafficLight));

        try {
            messageEmitter.send(message, BusChannel.TRAFFIC_LIGHTS_ORDER);
            state.registerSeenCar(car);
        } catch (IOException | TimeoutException e) {
            LOGGER.error("Exception occurred while sending a message to the bus: " + e);
        }
    }

    private void handlePassedCar(int trafficLight, int car) {
        // TODO: Implement the logic.
        LOGGER.trace("The car " + car + " passed the traffic light " + trafficLight + ".");
    }
}
