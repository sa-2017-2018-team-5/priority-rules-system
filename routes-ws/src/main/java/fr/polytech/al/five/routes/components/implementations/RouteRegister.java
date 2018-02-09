package fr.polytech.al.five.routes.components.implementations;

import fr.polytech.al.five.bus.BusChannel;
import fr.polytech.al.five.bus.BusInformation;
import fr.polytech.al.five.bus.PubSubEmitter;
import fr.polytech.al.five.messages.Message;
import fr.polytech.al.five.messages.RoutePlannedMessage;
import fr.polytech.al.five.routes.business.Car;
import fr.polytech.al.five.routes.business.CarStatus;
import fr.polytech.al.five.routes.business.Route;
import fr.polytech.al.five.routes.business.TrafficLight;
import fr.polytech.al.five.routes.components.RouteRegisterer;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

/**
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
@Stateless
public class RouteRegister implements RouteRegisterer {

    private static final Logger LOGGER = Logger.getLogger(RouteRegister.class);

    public void sendRoute(Car car, Route route) {
        String busAddress = System.getenv("BUS_ADDRESS");
        if (busAddress == null) {
            busAddress = "localhost";
        }

        BusInformation busInformation = new BusInformation(busAddress);
        PubSubEmitter messageEmitter = new PubSubEmitter(busInformation);

        Message routePlanned = new RoutePlannedMessage(
                car.getId(),
                car.getType().getPriority(),
                car.getType().getStatus().equals(CarStatus.EMERGENCY),
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
