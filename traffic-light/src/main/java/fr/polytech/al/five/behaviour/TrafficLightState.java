package fr.polytech.al.five.behaviour;

import fr.polytech.al.five.messages.contents.LightStatus;

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
    private LightStatus trafficLightStatus;
    private Set<Integer> expectedForcedRedLights;

    public TrafficLightState(int trafficLightId) {
        this.trafficLightId = trafficLightId;
        expectedCars = new HashSet<>();
        expectedForcedRedLights = new HashSet<>();
        trafficLightStatus = LightStatus.NORMAL;
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

    public LightStatus getTrafficLightStatus() { return trafficLightStatus; }

    public void setTrafficLightStatus(LightStatus status) { this.trafficLightStatus = status; }

    public void waitTrafficLight(int trafficLightId) { expectedForcedRedLights.add(trafficLightId); }

    public void stopWaitingTrafficLight(int trafficLightId) { expectedForcedRedLights.remove(trafficLightId); }

    public boolean isWaitingForcedRedLight(int trafficLightId) {
        return expectedForcedRedLights.contains(trafficLightId);
    }

    public boolean isTrafficLightReadyToTurnGreen() {
        return expectedForcedRedLights.isEmpty() &&
                trafficLightStatus.equals(LightStatus.WAITING_TO_TURN_GREEN);
    }
}
