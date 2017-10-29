package fr.polytech.al.five.components;

import fr.polytech.al.five.RouteRegisterer;
import fr.polytech.al.five.bus.BusChannel;
import fr.polytech.al.five.bus.BusInformation;
import fr.polytech.al.five.bus.MessageEmitter;
import fr.polytech.al.five.entities.Car;
import fr.polytech.al.five.entities.Route;
import fr.polytech.al.five.entities.TrafficLight;
import fr.polytech.al.five.messages.Message;
import fr.polytech.al.five.messages.RoutePlannedMessage;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.jms.JMSException;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

/**
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
@Stateless
public class RouteRegister implements RouteRegisterer {

    private static final Logger LOGGER = Logger.getLogger(RouteRegister.class);

    public void sendRoute(Car car, Route route) throws JMSException {
        // TODO: Set up the bus hostname.
        BusInformation busInformation = new BusInformation("172.17.0.2");
        MessageEmitter messageEmitter = new MessageEmitter(busInformation);

        Message routePlanned = new RoutePlannedMessage(
                car.getId(),
                route.getEncounteredLights()
                        .stream()
                        .map(TrafficLight::getId)
                        .collect(Collectors.toList()));

        try {
            messageEmitter.send(routePlanned, BusChannel.ROUTE_PLANNED);
            LOGGER.info("New route sent to the bus!");
        } catch (IOException e) {
            LOGGER.error("Output exception while sending message to the bus: " + e);
        } catch (TimeoutException e) {
            LOGGER.error("Timeout exception while sending message to the bus: " + e);
        }
    }
}
