package fr.polytech.al.five;

import javax.ejb.Local;
import fr.polytech.al.five.entities.Route;
import fr.polytech.al.five.entities.Position;

import java.util.Date;

@Local
public interface RouteBuilder {

    /**
     * Build a route between two positions.
     * @param from The departure place.
     * @param to The destination.
     * @param departureDate The departure date.
     * @return A route which fit the request.
     */
    Route getRoute(Position from, Position to, Date departureDate);
}