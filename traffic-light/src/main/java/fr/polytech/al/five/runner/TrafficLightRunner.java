package fr.polytech.al.five.runner;

import asg.cliche.ShellFactory;
import fr.polytech.al.five.actions.OnRoutePlanned;
import fr.polytech.al.five.behaviour.TrafficLightState;
import fr.polytech.al.five.bus.BusChannel;
import fr.polytech.al.five.bus.BusInformation;
import fr.polytech.al.five.bus.MessageConsumer;
import fr.polytech.al.five.bus.MessageEmitter;
import fr.polytech.al.five.messages.RoutePlannedMessage;
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

        // TODO: Set up the traffic light ID.
        TrafficLightState state = new TrafficLightState(123);

        LOGGER.info("CONSUMERS SET UP - Starting");

        // ROUTE_PLANNED messages consumption.
        MessageConsumer<RoutePlannedMessage> routePlannedConsumer
                = new MessageConsumer<>(busInformation);

        try {
            routePlannedConsumer.makeConsume(
                    BusChannel.ROUTE_PLANNED,
                    new OnRoutePlanned(state).getAction());
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
