package fr.polytech.al.five.runner;

import fr.polytech.al.five.actions.OnRoutePlanned;
import fr.polytech.al.five.actions.OnTrafficLightObservation;
import fr.polytech.al.five.behaviour.PropertiesLoader;
import fr.polytech.al.five.behaviour.TrafficLightsGroupState;
import fr.polytech.al.five.bus.BusChannel;
import fr.polytech.al.five.bus.BusInformation;
import fr.polytech.al.five.bus.PubSubConsumer;
import fr.polytech.al.five.messages.RoutePlannedMessage;
import fr.polytech.al.five.messages.TrafficLightObservationMessage;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TrafficLightsGroupRunner {

    private static final Logger LOGGER = Logger.getLogger(TrafficLightsGroupRunner.class);

    public static void main(String[] args) {

        String busAddress = System.getenv("BUS_ADDRESS");
        if (busAddress == null) {
            busAddress = "localhost";
        }

        PropertiesLoader properties = new PropertiesLoader();

        BusInformation busInformation = new BusInformation(busAddress);

        TrafficLightsGroupState state = new TrafficLightsGroupState(properties);

        // Set up the Traffic Lights Observation messages consumer.
        PubSubConsumer<TrafficLightObservationMessage> observationConsumer =
                new PubSubConsumer<>(busInformation);
        try {
            observationConsumer.makeConsume(BusChannel.TRAFFIC_LIGHT_OBSERVATION,
                    new OnTrafficLightObservation(busInformation, state).getAction());
        } catch (IOException | TimeoutException e) {
            LOGGER.error("Error while connecting to the bus: " + e);
            System.exit(1);
        }

        PubSubConsumer<RoutePlannedMessage> routePlannedConsumer
                = new PubSubConsumer<>(busInformation);

        try {
            routePlannedConsumer.makeConsume(BusChannel.ROUTE_PLANNED,
                    new OnRoutePlanned(state).getAction());
        } catch (IOException | TimeoutException e) {
            LOGGER.error("Error while connecting to the message bus: " + e);
            System.exit(1);
        }
    }
}
