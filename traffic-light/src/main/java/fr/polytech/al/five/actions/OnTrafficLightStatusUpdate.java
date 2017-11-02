package fr.polytech.al.five.actions;

import fr.polytech.al.five.behaviour.TrafficLightState;
import fr.polytech.al.five.bus.BusInformation;
import fr.polytech.al.five.bus.MessageEmitter;
import fr.polytech.al.five.messages.TrafficLightStatusMessage;
import fr.polytech.al.five.messages.contents.LightStatus;
import org.apache.log4j.Logger;

import java.util.function.Consumer;

public class OnTrafficLightStatusUpdate {

    private static final Logger LOGGER = Logger.getLogger(OnTrafficLightStatusUpdate.class);

    private TrafficLightState trafficLightState;
    private MessageEmitter messageEmitter;

    public OnTrafficLightStatusUpdate(BusInformation busInformation,
                                      TrafficLightState trafficLightState) {

        this.trafficLightState = trafficLightState;
        messageEmitter = new MessageEmitter(busInformation);
    }

    public Consumer<TrafficLightStatusMessage> getAction() {
        return message -> {
            LOGGER.info("Received a traffic light status update");
            if ( message.getLightStatus().equals(LightStatus.FORCED_RED) &&
                    trafficLightState.isWaitingForcedRedLight(message.getTrafficLightId())) {
                trafficLightState.stopWaitingTrafficLight(message.getTrafficLightId());
                LOGGER.info("Traffic light id: " + trafficLightState.getId() +
                            " stopped waiting for traffic light id: " + message.getTrafficLightId());
                if(trafficLightState.isTrafficLightReadyToTurnGreen()){
                    trafficLightState.setTrafficLightStatus(LightStatus.FORCED_GREEN);
                    LOGGER.info("Traffic light id: " + trafficLightState.getId() +
                                "changed to forced green");

                }
            }
        };
    }
}
