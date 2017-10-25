package fr.polytech.al.five;

import fr.polytech.al.five.entities.Car;
import fr.polytech.al.five.entities.Route;

import javax.ejb.Local;
import javax.jms.JMSException;

@Local
public interface RouteRegisterer {

    /**
     * Send a route in the whole traffic lights network to prevent a car arrival.
     * @param car The car which queried the route.
     * @param route The sent route.
     */
    void sendRoute(Car car, Route route) throws JMSException;
}
