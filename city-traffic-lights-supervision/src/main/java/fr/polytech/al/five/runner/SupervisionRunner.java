package fr.polytech.al.five.runner;

import fr.polytech.al.five.actions.OnTrafficLightStatusUpdate;
import fr.polytech.al.five.behaviour.SupervisionState;
import fr.polytech.al.five.bus.BusChannel;
import fr.polytech.al.five.bus.BusInformation;
import fr.polytech.al.five.bus.MessageConsumer;
import fr.polytech.al.five.messages.TrafficLightStatusMessage;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class SupervisionRunner {

    private static final Logger LOGGER = Logger.getLogger(SupervisionRunner.class);

    public static void main(String[] args) throws IOException {
        SupervisionState state = new SupervisionState();


        String busAddress = System.getenv("BUS_ADDRESS");
        if (busAddress == null) {
            busAddress = "localhost";
        }

        BusInformation busInformation = new BusInformation(busAddress);

        MessageConsumer<TrafficLightStatusMessage> trafficLightStatusConsumer =
                new MessageConsumer<>(busInformation);

        try {
            trafficLightStatusConsumer.makeConsume(
                    BusChannel.SUPERVISION,
                    new OnTrafficLightStatusUpdate(state).getAction()
            );
        } catch (TimeoutException e) {
            LOGGER.error("Time out when attempting to connect the bus: " + e);
            System.exit(1);
        }

        LOGGER.info("CONSUMER SET UP - Done");
    }
}
