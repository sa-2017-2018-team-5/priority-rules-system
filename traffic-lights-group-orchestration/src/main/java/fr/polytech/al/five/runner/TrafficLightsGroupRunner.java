package fr.polytech.al.five.runner;

import fr.polytech.al.five.actions.OnTrafficLightObservation;
import fr.polytech.al.five.behaviour.TrafficLightsGroupState;
import fr.polytech.al.five.bus.BusChannel;
import fr.polytech.al.five.bus.BusInformation;
import fr.polytech.al.five.bus.MessageConsumer;
import fr.polytech.al.five.messages.TrafficLightObservationMessage;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TrafficLightsGroupRunner {

    private static final Logger LOGGER = Logger.getLogger(TrafficLightsGroupRunner.class);

    public static void main(String[] args) {
        BusInformation busInformation = new BusInformation("172.17.0.2");

        TrafficLightsGroupState state = new TrafficLightsGroupState();

        // Set up the Traffic Lights Observation messages consumer.
        MessageConsumer<TrafficLightObservationMessage> observationConsumer =
                new MessageConsumer<>(busInformation);
        try {
            observationConsumer.makeConsume(BusChannel.TRAFFIC_LIGHT_OBSERVATION,
                    new OnTrafficLightObservation(busInformation, state).getAction());
        } catch (IOException | TimeoutException e) {
            LOGGER.error("Error while connecting to the bus: " + e);
            System.exit(1);
        }
    }
}
