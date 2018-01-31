package fr.polytech.al.five.behaviour;

import fr.polytech.al.five.data.DBClient;
import fr.polytech.al.five.model.TrafficGroup;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
public class TrafficLightsGroupState {

    private PropertiesLoader properties;
    private DBClient dbClient;

    private static final Logger LOGGER = Logger.getLogger(TrafficLightsGroupState.class);

    public TrafficLightsGroupState(PropertiesLoader properties) {
        this.properties = properties;
        dbClient = new DBClient();
        LOGGER.info("silence");
    }



    public void acknowledgeRoute(KnownCar car, List<Integer> encounteredTrafficLights) {
        dbClient.saveCar(car);
        dbClient.saveCarRoute(car.getId(), encounteredTrafficLights);
    }

    public boolean knowsTrafficLight(int trafficLightId) {
        return properties.getTrafficGroups().stream().anyMatch(
                trafficGroup -> trafficGroup.getTrafficID().contains(trafficLightId));
    }

    public List<Integer> mustBecomeRed(int askGreen) {
        List<Integer> toRed = new ArrayList<>();
        Optional<TrafficGroup> oGroup = properties.getTrafficGroups().stream()
                .filter(trafficGroup -> trafficGroup.getTrafficID().contains(askGreen)).findFirst();
        if (oGroup.isPresent()) {
            TrafficGroup trafficGroup = oGroup.get();
            toRed.addAll(trafficGroup.getRules().get(askGreen));
        }
        return toRed;
    }

    public List<Integer> mustBecomeGreen(int askGreen) {
        List<Integer> toGreen = new ArrayList<>();
        Optional<TrafficGroup> oGroup = properties.getTrafficGroups().stream()
                .filter(trafficGroup -> trafficGroup.getTrafficID().contains(askGreen)).findFirst();
        if (oGroup.isPresent()) {
            TrafficGroup trafficGroup = oGroup.get();
            toGreen.addAll(trafficGroup.getTrafficID());
        }
        toGreen.removeAll(mustBecomeRed(askGreen));
        return toGreen;
    }

    public boolean isWaitingCar(int carId) {
        return dbClient.isRouteDefinedForCar(carId);
    }

    public void registerSeenCar(int trafficLight, int carId) {
        updateCarRoute(trafficLight, carId);

        makeTrafficLightWaitCar(trafficLight, carId);
    }

    private void makeTrafficLightWaitCar(int trafficLight, int car) {
        List<Integer> concernedTrafficLights = new ArrayList<>();
        concernedTrafficLights.addAll(mustBecomeGreen(trafficLight));
        concernedTrafficLights.addAll(mustBecomeRed(trafficLight));

        dbClient.saveCarInfluence(car, concernedTrafficLights);
    }

    private void updateCarRoute(int trafficLight, int carId) {
        List<Integer> route = dbClient.retrieveCarRoute(carId);

        while (!route.isEmpty() && route.get(0) != trafficLight) {
            route.remove(0);
        }

        if (!route.isEmpty()) {
            route.remove(0);
            dbClient.updateRoute(carId, route);
        } else {
            dbClient.removeRoute(carId);
        }

    }

    public KnownCar getCar(int carId) {
        return dbClient.retrieveCar(carId);
    }

    public boolean isBusyIntersection(int trafficLight) {
        return dbClient.isTrafficLightInfluenced(trafficLight);
    }

    private int getTrafficLightGroup(int trafficLight) {
        Optional<TrafficGroup> oGroup = properties.getTrafficGroups().stream()
                .filter(trafficGroup -> trafficGroup.getTrafficID().contains(trafficLight)).findFirst();

        return oGroup.get().getGroupId();
    }

    public void addPendingRequest(int carId, int priority, int trafficLight) {
        int groupId = getTrafficLightGroup(trafficLight);
        dbClient.saveRequest(carId, priority, groupId, trafficLight);
    }

    public boolean isCarWaitingAt(int trafficLight) {
        int groupId = getTrafficLightGroup(trafficLight);

        return dbClient.isPendingRequests(groupId);
    }

    public CarQuery removePendingRequest(int trafficLight) {
        int groupId = getTrafficLightGroup(trafficLight);
        CarQuery query = dbClient.getHighestPriorityRequest(groupId);
        dbClient.removePendingRequest(query.getCarId(), query.getTrafficLightId());

        return query;
    }

    public void removeCarInfluence(int carId){
        dbClient.removeCarInfluence(carId);
    }
}
