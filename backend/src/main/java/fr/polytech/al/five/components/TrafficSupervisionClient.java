package fr.polytech.al.five.components;

import fr.polytech.al.five.TrafficSupervisor;
import fr.polytech.al.five.entities.TrafficInformation;

import javax.ejb.Stateless;

@Stateless
public class TrafficSupervisionClient implements TrafficSupervisor {

    @Override
    public TrafficInformation getTraffic() {
        // TODO: Connect to the external service.
        return new TrafficInformation();
    }
}
