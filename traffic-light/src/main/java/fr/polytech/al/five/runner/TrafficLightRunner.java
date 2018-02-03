package fr.polytech.al.five.runner;

import asg.cliche.ShellFactory;
import fr.polytech.al.five.actions.OnOrchestrationOrder;
import fr.polytech.al.five.actions.OnTrafficLightStatusUpdate;
import fr.polytech.al.five.behaviour.TrafficLightState;
import fr.polytech.al.five.bus.BusChannel;
import fr.polytech.al.five.bus.BusInformation;
import fr.polytech.al.five.bus.PubSubConsumer;
import fr.polytech.al.five.bus.PubSubEmitter;
import fr.polytech.al.five.messages.TrafficLightOrdersMessage;
import fr.polytech.al.five.messages.TrafficLightStatusMessage;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
public class TrafficLightRunner {

    private static final Logger LOGGER = Logger.getLogger(TrafficLightRunner.class);

    public static void main(String[] args) throws IOException {
        String busAddress = System.getenv("BUS_ADDRESS");
        if (busAddress == null) {
            busAddress = "localhost";
        }

        BusInformation busInformation = new BusInformation(busAddress);

        String stringTrafficLightId = System.getenv("TRAFFIC_LIGHT_ID");
        Integer trafficLightId;
        if (stringTrafficLightId == null) {
            trafficLightId = -1;
        } else {
            trafficLightId = Integer.parseInt(stringTrafficLightId);
        }
        TrafficLightState state = new TrafficLightState(trafficLightId, new PubSubEmitter(busInformation));

        LOGGER.info("CONSUMERS SET UP - Starting the traffic light");

        // TRAFFIC_LIGHT_STATUS messages consumption.
        PubSubConsumer<TrafficLightStatusMessage> trafficLightStatusConsumer
                = new PubSubConsumer<>(busInformation);

        // CAR_STATUS messages consumption.
        PubSubConsumer<TrafficLightOrdersMessage> carStatusConsumer
                = new PubSubConsumer<>(busInformation);

        try {
            trafficLightStatusConsumer.makeConsume(
                    BusChannel.TRAFFIC_LIGHT_STATUS,
                    new OnTrafficLightStatusUpdate(state).getAction());
            carStatusConsumer.makeConsume(
                    BusChannel.TRAFFIC_LIGHTS_ORDER,
                    new OnOrchestrationOrder(busInformation, state).getAction());
        } catch (TimeoutException e) {
            LOGGER.error("Time out when attempting to connect the bus: " + e);
            System.exit(1);
        }

        LOGGER.info("CONSUMERS SET UP - Done");

        TrafficLightInterface commands = new TrafficLightInterface(
                new PubSubEmitter(busInformation),
                state);

        // Start the shell.
        ShellFactory.createConsoleShell("traffic-light",
                "Traffic Light",
                commands)
                .commandLoop();
    }
}
