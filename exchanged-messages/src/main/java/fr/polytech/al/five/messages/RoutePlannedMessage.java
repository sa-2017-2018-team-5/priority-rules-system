package fr.polytech.al.five.messages;

import java.util.List;

/**
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
public class RoutePlannedMessage implements Message {

    private final int carId;
    private final int carPriority;
    private final boolean isEmergency;
    private final List<Integer> encounteredTrafficLights;

    public RoutePlannedMessage(int carId, int carPriority, boolean isEmergency, List<Integer> encounteredTrafficLights) {
        this.carId = carId;
        this.carPriority = carPriority;
        this.isEmergency = isEmergency;
        this.encounteredTrafficLights = encounteredTrafficLights;
    }

    public int getCarId() {
        return carId;
    }

    public int getCarPriority() {
        return carPriority;
    }

    public boolean isEmergency() {
        return isEmergency;
    }

    public List<Integer> getEncounteredTrafficLights() {
        return encounteredTrafficLights;
    }
}
