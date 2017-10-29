package fr.polytech.al.five.behaviour;

import java.util.HashSet;
import java.util.Set;

/**
 * The state of the traffic light. For now, this is stateful but we may make it
 * stateless in a future release.
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
public class TrafficLightState {

    private final int trafficLightId;
    private Set<Integer> expectedCars;

    public TrafficLightState(int trafficLightId) {
        this.trafficLightId = trafficLightId;
        expectedCars = new HashSet<>();
    }

    public void waitCar(int carId) {
        expectedCars.add(carId);
    }

    public void stopWaitCar(int carId) {
        expectedCars.remove(carId);
    }

    public boolean isWaitingCar(int carId) {
        return expectedCars.contains(carId);
    }

    public int getId() {
        return trafficLightId;
    }
}
