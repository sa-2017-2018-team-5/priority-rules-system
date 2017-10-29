package fr.polytech.al.five.test;

import fr.polytech.al.five.bus.BusChannel;
import fr.polytech.al.five.bus.BusInformation;
import fr.polytech.al.five.bus.MessageEmitter;
import fr.polytech.al.five.messages.CarAction;
import fr.polytech.al.five.messages.TrafficLightObservationMessage;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
public class TestEmitter {

    public static void main(String[] args) throws IOException, TimeoutException {
        BusInformation busInformation = new BusInformation("172.17.0.2");

        MessageEmitter messageEmitter = new MessageEmitter(busInformation);

        TrafficLightObservationMessage message = new TrafficLightObservationMessage(158, 741, CarAction.SEEN);

        messageEmitter.send(message, BusChannel.TRAFFIC_LIGHT_OBSERVATION);
    }
}
