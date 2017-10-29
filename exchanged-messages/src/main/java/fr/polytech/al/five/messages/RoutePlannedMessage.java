package fr.polytech.al.five.messages;

import java.util.List;

/**
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
public class RoutePlannedMessage extends Message {

    private final int carId;
    private final List<Integer> encounteredTrafficLights;

    public RoutePlannedMessage(int carId, List<Integer> encounteredTrafficLights) {
        this.carId = carId;
        this.encounteredTrafficLights = encounteredTrafficLights;
    }

    public int getCarId() {
        return carId;
    }

    public List<Integer> getEncounteredTrafficLights() {
        return encounteredTrafficLights;
    }
}
