package fr.polytech.al.five.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import util.EventEmitter;
import fr.polytech.al.five.RouteRegisterer;
import fr.polytech.al.five.entities.Car;
import fr.polytech.al.five.entities.Route;
import org.json.JSONObject;

import javax.ejb.Stateless;
import javax.jms.JMSException;
import java.io.IOException;
import java.util.Map;

@Stateless
public class RouteRegister implements RouteRegisterer {

    public void sendRoute(Car car, Route route) throws JMSException {
        // TODO: Implement the message bus.
        EventEmitter eventEmitter = new EventEmitter("CityExchange");
        ObjectMapper mapper = new ObjectMapper();
        JSONObject message = new JSONObject();

        message.put("id", "City");
        message.put("car", mapper.convertValue(car, Map.class));
        message.put("route", mapper.convertValue(route, Map.class));

        try {
            eventEmitter.publish(message.toString().getBytes("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        eventEmitter.close();
    }
}
