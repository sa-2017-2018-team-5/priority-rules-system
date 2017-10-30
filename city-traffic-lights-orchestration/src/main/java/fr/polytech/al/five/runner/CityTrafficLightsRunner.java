package fr.polytech.al.five.runner;

import fr.polytech.al.five.actions.OnRoutePlanned;
import fr.polytech.al.five.bus.BusChannel;
import fr.polytech.al.five.bus.BusInformation;
import fr.polytech.al.five.bus.MessageConsumer;
import fr.polytech.al.five.messages.RoutePlannedMessage;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class CityTrafficLightsRunner {

    private static final Logger LOGGER = Logger.getLogger(CityTrafficLightsRunner.class);

    public static void main(String[] args) {
        String busAddress = System.getenv("BUS_ADDRESS");
        if (busAddress == null) {
            busAddress = "localhost";
        }

        BusInformation busInformation = new BusInformation(busAddress);

        MessageConsumer<RoutePlannedMessage> routePlannedConsumer
                = new MessageConsumer<>(busInformation);

        try {
            routePlannedConsumer.makeConsume(BusChannel.ROUTE_PLANNED,
                    new OnRoutePlanned().getAction());
        } catch (IOException | TimeoutException e) {
            LOGGER.error("Error while connecting to the message bus: " + e);
            System.exit(1);
        }
    }
}
