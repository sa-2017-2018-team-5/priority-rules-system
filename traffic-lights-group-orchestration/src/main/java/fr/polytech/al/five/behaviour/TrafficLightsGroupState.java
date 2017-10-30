package fr.polytech.al.five.behaviour;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
public class TrafficLightsGroupState {

    private List<Integer> includedTrafficLights;
    private List<Integer> seenCars;

    public TrafficLightsGroupState() {
        // TODO: Get the known traffic lights from configuration.
        includedTrafficLights = Arrays.asList(1, 2, 3);
        seenCars = new ArrayList<>();
    }

    public boolean knowsTrafficLight(int trafficLightId) {
        return includedTrafficLights.contains(trafficLightId);
    }

    public List<Integer> mustBecomeRed(int askGreen) {
        if (askGreen == 1) {
            return Arrays.asList(2, 3);
        } else {
            return Collections.singletonList(1);
        }
    }

    public List<Integer> mustBecomeGreen(int askGreen) {
        if (askGreen == 1){
            return Collections.singletonList(1);
        } else {
            return Arrays.asList(2, 3);
        }
    }

    public void registerSeenCar(int carId) {
        seenCars.add(carId);
    }
}
