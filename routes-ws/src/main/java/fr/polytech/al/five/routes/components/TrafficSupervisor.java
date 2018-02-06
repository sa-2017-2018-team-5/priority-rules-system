package fr.polytech.al.five.routes.components;

import fr.polytech.al.five.routes.business.TrafficInformation;

import javax.ejb.Local;

@Local
public interface TrafficSupervisor {

    /**
     * Fetch the traffic status from an external service.
     * @return A traffic descriptor.
     */
    TrafficInformation getTraffic(String request);
}