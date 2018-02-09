package fr.polytech.al.five.tlws.components.implementations;

import fr.polytech.al.five.bus.BusChannel;
import fr.polytech.al.five.bus.BusInformation;
import fr.polytech.al.five.bus.TaskEmitter;
import fr.polytech.al.five.messages.Message;
import fr.polytech.al.five.messages.TrafficLightObservationMessage;
import fr.polytech.al.five.messages.contents.CarAction;
import fr.polytech.al.five.tlws.components.BusCommunication;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Stateless
public class BusCommunicationImplementation implements BusCommunication {

    private static final Logger LOGGER = Logger.getLogger(BusCommunicationImplementation.class);

    @Override
    public void sendCarStatus(int trafficLightId, int carId, CarAction status) {
        String busAddress = System.getenv("BUS_ADDRESS");
        if (busAddress == null) {
            busAddress = "localhost";
        }

        BusInformation busInformation = new BusInformation(busAddress);
        TaskEmitter taskEmitter = new TaskEmitter(busInformation);

        Message carActionMessage = new TrafficLightObservationMessage(trafficLightId, carId, status);

        try {
            taskEmitter.send(carActionMessage, BusChannel.TRAFFIC_LIGHT_OBSERVATION_TASK);
            taskEmitter.send(carActionMessage, BusChannel.TRAFFIC_LIGHT_OBSERVATION);
            LOGGER.info("New route sent to the bus!");
        } catch (IOException e) {
            LOGGER.error("Output exception while sending message to the bus: " + e);
        } catch (TimeoutException e) {
            LOGGER.error("Timeout exception while sending message to the bus: " + e);
        }
    }
}
