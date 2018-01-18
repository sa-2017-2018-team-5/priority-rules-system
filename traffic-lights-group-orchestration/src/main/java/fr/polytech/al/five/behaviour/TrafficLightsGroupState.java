package fr.polytech.al.five.behaviour;

import java.util.*;

/**
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
public class TrafficLightsGroupState {

    private PropertiesLoader properties;
    private final Map<Integer, List<Integer>> carToRoute;
    private final List<List<Integer>> busyTrafficLights;

    public TrafficLightsGroupState(PropertiesLoader properties){
        this.properties = properties;
        carToRoute = new HashMap<>();
        busyTrafficLights = new ArrayList<>();
    }

    public void acknowledgeRoute(int carId, List<Integer> encounteredTrafficLights) {
        carToRoute.put(carId, encounteredTrafficLights);
    }

    public boolean knowsTrafficLight(int trafficLightId) {
        return this.properties.getTrafficLights().contains(trafficLightId);
    }

    public List<Integer> mustBecomeRed(int askGreen) {
        return this.properties.getTrafficRules().get(askGreen);
    }

    public List<Integer> mustBecomeGreen(int askGreen) {
        List<Integer> tmp = new ArrayList<>();

        for (int trafficLight : this.properties.getTrafficLights()){
            if (!mustBecomeRed(askGreen).contains(trafficLight)) {
                tmp.add(trafficLight);
            }
        }

        return tmp;
    }

    public boolean isWaitingCar(int carId) {
        return carToRoute.containsKey(carId);
    }

    public void registerSeenCar(int trafficLight, int carId) {
        updateCarRoute(trafficLight, carId);
    }

    private void updateCarRoute(int trafficLight, int carId) {
        List<Integer> route = carToRoute.get(carId);

        while (!route.isEmpty() && route.get(0) != trafficLight) {
            route.remove(0);
        }

        if (!route.isEmpty()) {
            route.remove(0);
        }
    }

    public boolean isBusyIntersection(int trafficLight) {
        return busyTrafficLights.stream()
                .flatMap(Collection::stream)
                .anyMatch(tl -> tl == trafficLight);
    }

    public void addQuery(int trafficLight, int carId) {
        // TODO
    }
}
