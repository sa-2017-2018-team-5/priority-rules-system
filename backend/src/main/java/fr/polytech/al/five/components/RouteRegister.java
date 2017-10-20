package fr.polytech.al.five.components;

import fr.polytech.al.five.RouteRegisterer;
import fr.polytech.al.five.entities.Car;
import fr.polytech.al.five.entities.Route;

import javax.ejb.Stateless;

@Stateless
public class RouteRegister implements RouteRegisterer {

    @Override
    public void sendRoute(Car car, Route route) {
        // TODO: Implement the message bus.
    }
}
