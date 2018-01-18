package fr.polytech.al.five.behaviour;

import java.util.*;

/**
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
public class TrafficLightsGroupState {

    private PropertiesLoader properties;
    private final Map<Integer, KnownCar> knownCars;
    private final Map<Integer, List<Integer>> carToRoute;
    private final Map<Integer, List<Integer>> busyTrafficLights;

    public TrafficLightsGroupState(PropertiesLoader properties) {
        this.properties = properties;
        knownCars = new HashMap<>();
        carToRoute = new HashMap<>();
        busyTrafficLights = new HashMap<>();
    }

    public void acknowledgeRoute(KnownCar car, List<Integer> encounteredTrafficLights) {
        carToRoute.put(car.id, encounteredTrafficLights);
        knownCars.put(car.id, car);
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

        // TODO Change the registration to keep into account the fact that multiple car use the intersection.
        List<Integer> concernedTrafficLights = new ArrayList<>();
        concernedTrafficLights.addAll(mustBecomeGreen(trafficLight));
        concernedTrafficLights.addAll(mustBecomeRed(trafficLight));

        busyTrafficLights.put(carId, concernedTrafficLights);
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
        return busyTrafficLights.values().stream()
                .flatMap(Collection::stream)
                .anyMatch(tl -> tl == trafficLight);
    }

    public void addQuery(int trafficLight, int carId) {
        // TODO Keep in mind the query until the intersection is not busy anymore.
    }

    public static class KnownCar {

        private final int id;
        private final int priority;
        private final boolean isEmergency;

        public KnownCar(int id, int priority, boolean isEmergency) {
            this.id = id;
            this.priority = priority;
            this.isEmergency = isEmergency;
        }

        public int getId() {
            return id;
        }

        public int getPriority() {
            return priority;
        }

        public boolean isEmergency() {
            return isEmergency;
        }
    }
}
