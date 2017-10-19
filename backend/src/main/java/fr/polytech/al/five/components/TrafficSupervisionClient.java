package fr.polytech.al.five.components;

import fr.polytech.al.five.TrafficSupervision;
import fr.polytech.al.five.entities.TrafficInformation;

import javax.ejb.Stateless;

@Stateless
public class TrafficSupervisionClient implements TrafficSupervision {

    @Override
    public TrafficInformation getTraffic() {
        // TODO: Connect to the external service.
        return new TrafficInformation();
    }
}
