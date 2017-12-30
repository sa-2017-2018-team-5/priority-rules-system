package fr.polytech.al.five.behaviour;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
public class TrafficLightsGroupState {

    private List<Integer> seenCars;

    public TrafficLightsGroupState() {
        seenCars = new ArrayList<>();
    }

    public boolean knowsTrafficLight(int trafficLightId) {
        return PropertiesLoader.getTrafficLights().contains(trafficLightId);
    }

    public List<Integer> mustBecomeRed(int askGreen) {
        return PropertiesLoader.getTrafficRules().get(askGreen);
    }

    public List<Integer> mustBecomeGreen(int askGreen) {
        List<Integer> tmp = new ArrayList<>();
        for (int tl : PropertiesLoader.getTrafficLights()){
            if (!mustBecomeRed(askGreen).contains(tl)) {
                tmp.add(tl);
            }
        }
        return tmp;
    }

    public void registerSeenCar(int carId) {
        seenCars.add(carId);
    }
}
