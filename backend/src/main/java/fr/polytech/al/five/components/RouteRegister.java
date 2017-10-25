package fr.polytech.al.five.components;

import fr.polytech.al.five.RouteRegisterer;
import fr.polytech.al.five.entities.Car;
import fr.polytech.al.five.entities.Route;
import fr.polytech.al.five.util.EventEmitter;
import org.json.JSONObject;

import javax.ejb.Stateless;
import java.io.IOException;

@Stateless
public class RouteRegister implements RouteRegisterer {

    @Override
    public void sendRoute(Car car, Route route) {
        // TODO: Implement the message bus.
        EventEmitter eventEmitter = new EventEmitter();

        JSONObject jsonCar = new JSONObject(car);
        JSONObject jsonRoute = new JSONObject(route);
        JSONObject message = new JSONObject();

        message.put("id", "City");
        message.put("car", jsonCar);
        message.put("route", jsonRoute);

        try {
            eventEmitter.publish(message.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        eventEmitter.close();
    }
}
