package fr.polytech.al.five.routes.components;

import fr.polytech.al.five.routes.business.Position;
import fr.polytech.al.five.routes.business.Route;

import javax.ejb.Local;
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
    Route makeRoute(Integer id, Position from, Position to, Date departureDate);
}