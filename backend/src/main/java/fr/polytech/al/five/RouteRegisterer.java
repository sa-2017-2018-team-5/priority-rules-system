package fr.polytech.al.five;

import fr.polytech.al.five.entities.Car;
import fr.polytech.al.five.entities.Route;

import javax.ejb.Local;

@Local
public interface RouteRegisterer {

    void sendRoute(Car car, Route route);
}
