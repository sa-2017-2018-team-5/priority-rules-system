package fr.polytech.al.five;

import javax.ejb.Local;
import fr.polytech.al.five.entities.TrafficInformation;

@Local
public interface TrafficSupervision {

    TrafficInformation getTraffic();
}