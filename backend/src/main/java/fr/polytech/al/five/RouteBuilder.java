package fr.polytech.al.five;

import javax.ejb.Local;
import fr.polytech.al.five.entities.Route;
import fr.polytech.al.five.entities.Position;

import java.util.Date;

@Local
public interface RouteBuilder {

    Route getRoute(Position from, Position to, Date departureDate);
}