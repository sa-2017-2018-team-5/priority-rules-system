package fr.polytech.al.five;

import javax.ejb.Local;
import fr.polytech.al.five.entities.TrafficInformation;

@Local
public interface TrafficSupervisor {

    /**
     * Fetch the traffic status from an external service.
     * @return A traffic descriptor.
     */
    TrafficInformation getTraffic();
}