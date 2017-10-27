package fr.polytech.al.five.components;

import fr.polytech.al.five.RouteRegisterer;
import fr.polytech.al.five.entities.Car;
import fr.polytech.al.five.entities.Route;
import fr.polytech.al.five.message.CarInfo;
import fr.polytech.al.five.message.TrafficLightInfo;
import fr.polytech.al.five.message.TrafficMessage;
import fr.polytech.al.five.util.EventEmitter;
import fr.polytech.al.five.util.MessageMarshaller;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.jms.JMSException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
@Stateless
public class RouteRegister implements RouteRegisterer {

    private static final Logger LOGGER = Logger.getLogger(RouteRegister.class);

    public void sendRoute(Car car, Route route) throws JMSException {
        EventEmitter eventEmitter = new EventEmitter("Routes");

        CarInfo carInfo = new CarInfo(
                car.getId(),
                car.getType().getName(),
                car.getType().getPriority(),
                car.getType().getStatus().name()
        );

        List<TrafficLightInfo> trafficLightInfos = new ArrayList<>();

        route.getEncounteredLights().forEach(trafficLight ->
            trafficLightInfos.add(new TrafficLightInfo(trafficLight.getId())));

        TrafficMessage trafficMessage = new TrafficMessage(
                carInfo,
                trafficLightInfos,
                route.getDeparture()
        );

        try {
            eventEmitter.publish(MessageMarshaller.construct(trafficMessage).getBytes("UTF-8"));
        } catch (IOException e) {
            LOGGER.error("Error when sending the route to the bus: " + e);
        }

        eventEmitter.close();
    }
}
