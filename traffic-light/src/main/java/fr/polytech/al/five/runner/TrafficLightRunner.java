package fr.polytech.al.five.runner;

import asg.cliche.ShellFactory;
import fr.polytech.al.five.actions.OnCarStatusUpdate;
import fr.polytech.al.five.actions.OnRoutePlanned;
import fr.polytech.al.five.actions.OnTrafficLightStatusUpdate;
import fr.polytech.al.five.behaviour.TrafficLightState;
import fr.polytech.al.five.bus.BusChannel;
import fr.polytech.al.five.bus.BusInformation;
import fr.polytech.al.five.bus.MessageConsumer;
import fr.polytech.al.five.bus.MessageEmitter;
import fr.polytech.al.five.messages.RoutePlannedMessage;
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
        TrafficLightState state = new TrafficLightState(trafficLightId);

        LOGGER.info("CONSUMERS SET UP - Starting");

        // ROUTE_PLANNED messages consumption.
        MessageConsumer<RoutePlannedMessage> routePlannedConsumer
                = new MessageConsumer<>(busInformation);

        // TRAFFIC_LIGHT_STATUS messages consumption.
        MessageConsumer<TrafficLightStatusMessage> trafficLightStatusConsumer
                = new MessageConsumer<>(busInformation);

        // CAR_STATUS messages consumption.
        MessageConsumer<TrafficLightOrdersMessage> carStatusConsumer
                = new MessageConsumer<>(busInformation);

        try {
            routePlannedConsumer.makeConsume(
                    BusChannel.ROUTE_PLANNED,
                    new OnRoutePlanned(state).getAction());
            trafficLightStatusConsumer.makeConsume(
                    BusChannel.TRAFFIC_LIGHT_STATUS,
                    new OnTrafficLightStatusUpdate(busInformation, state).getAction());
            carStatusConsumer.makeConsume(
                    BusChannel.TRAFFIC_LIGHTS_ORDER,
                    new OnCarStatusUpdate(busInformation, state).getAction());
        } catch (TimeoutException e) {
            LOGGER.error("Time out when attempting to connect the bus: " + e);
            System.exit(1);
        }

        LOGGER.info("CONSUMERS SET UP - Done");

        TrafficLightInterface commands = new TrafficLightInterface(
                new MessageEmitter(busInformation),
                state);

        // Start the shell.
        ShellFactory.createConsoleShell("traffic-light",
                "Traffic Light",
                commands)
                .commandLoop();
    }
}
