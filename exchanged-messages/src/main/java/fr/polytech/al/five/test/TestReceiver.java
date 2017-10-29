package fr.polytech.al.five.test;

import fr.polytech.al.five.bus.BusChannel;
import fr.polytech.al.five.bus.BusInformation;
import fr.polytech.al.five.bus.MessageConsumer;
import fr.polytech.al.five.messages.TrafficLightObservationMessage;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
public class TestReceiver {

    public static void main(String[] args) throws IOException, TimeoutException {
        BusInformation busInformation = new BusInformation("172.17.0.2");

        MessageConsumer<TrafficLightObservationMessage> messageConsumer =
                new MessageConsumer<>(busInformation);

        System.out.println("Prepare listening...");

        messageConsumer.makeConsume(BusChannel.TRAFFIC_LIGHT_OBSERVATION, message -> {
            System.out.println("Message received!");
            System.out.println("* Car ID: " + message.getCarId());
            System.out.println("* Traffic Light ID: " + message.getTrafficLightId());
            System.out.println("* Car Action: " + message.getCarAction().toString());
            System.out.println();
        });

        System.out.println("Listening the bus.\n");
    }
}
