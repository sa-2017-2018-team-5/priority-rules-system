package fr.polytech.al.five.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.polytech.al.five.message.CarInfo;
import fr.polytech.al.five.message.TrafficLightInfo;
import fr.polytech.al.five.message.TrafficMessage;
import fr.polytech.al.five.util.EventEmitter;
import fr.polytech.al.five.RouteRegisterer;
import fr.polytech.al.five.entities.Car;
import fr.polytech.al.five.entities.Route;
import org.json.JSONObject;

import javax.ejb.Stateless;
import javax.jms.JMSException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
@Stateless
public class RouteRegister implements RouteRegisterer {

    public void sendRoute(Car car, Route route) throws JMSException {
        EventEmitter eventEmitter = new EventEmitter("CityExchange");
        ObjectMapper mapper = new ObjectMapper();
        JSONObject message = new JSONObject();

        CarInfo carInfo = new CarInfo(
                car.getId(),
                car.getType().getName(),
                car.getType().getPriority(),
                car.getType().getStatus().name()
        );

        List<TrafficLightInfo> trafficLightInfos = new ArrayList<>();

        route.getEncounteredLights().forEach(trafficLight -> {
            trafficLightInfos.add(new TrafficLightInfo(trafficLight.getId()));
        });

        TrafficMessage trafficMessage = new TrafficMessage(
                carInfo,
                trafficLightInfos,
                route.getDeparture()
        );


        message.put("id", "City");
        message.put("message", mapper.convertValue(trafficMessage, Map.class));

        try {
            eventEmitter.publish(message.toString().getBytes("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        eventEmitter.close();
    }
}
