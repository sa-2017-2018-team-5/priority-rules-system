package fr.polytech.al.five.messages;

import fr.polytech.al.five.messages.contents.CarAction;

/**
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
public class TrafficLightObservationMessage extends Message {

    private final int trafficLightId;
    private final int carId;
    private final CarAction carAction;

    public TrafficLightObservationMessage(int trafficLightId, int carId, CarAction carAction) {
        this.trafficLightId = trafficLightId;
        this.carId = carId;
        this.carAction = carAction;
    }

    public int getTrafficLightId() {
        return trafficLightId;
    }

    public int getCarId() {
        return carId;
    }

    public CarAction getCarAction() {
        return carAction;
    }
}
