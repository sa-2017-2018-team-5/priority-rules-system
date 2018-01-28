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
    //private final Map<Integer, KnownCar> knownCars;
    //private final Map<Integer, List<Integer>> carToRoute;
    //private final Map<Integer, List<Integer>> busyTrafficLights;
    //private final Map<Integer, Set<Integer>> trafficLightToCurrentCars;
    //private final List<CarQuery> queries;
    //private final Map<Integer, List<KnownCar>> pendingRequests;
    private DBClient dbClient;

    private static final Logger LOGGER = Logger.getLogger(TrafficLightsGroupState.class);

    public TrafficLightsGroupState(PropertiesLoader properties) {
        this.properties = properties;
        dbClient = new DBClient();
        //knownCars = new HashMap<>();
        //carToRoute = new HashMap<>();
        //busyTrafficLights = new HashMap<>();
        //trafficLightToCurrentCars = new HashMap<>();
        //queries = new ArrayList<>();
        //pendingRequests = new HashMap<>();
        LOGGER.info("silence");
    }



    public void acknowledgeRoute(KnownCar car, List<Integer> encounteredTrafficLights) {
        dbClient.saveCar(car);
        dbClient.saveCarRoute(car.getId(), encounteredTrafficLights);
        //carToRoute.put(car.getId(), encounteredTrafficLights);
        //knownCars.put(car.getId(), car);
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
        //return carToRoute.containsKey(carId);
    }

    public void registerSeenCar(int trafficLight, int carId) {
        updateCarRoute(trafficLight, carId);

        makeTrafficLightWaitCar(trafficLight, carId);
    }

    public void makeTrafficLightWaitCar(int trafficLight, int car) {
        List<Integer> concernedTrafficLights = new ArrayList<>();
        concernedTrafficLights.addAll(mustBecomeGreen(trafficLight));
        concernedTrafficLights.addAll(mustBecomeRed(trafficLight));

        //busyTrafficLights.put(car, concernedTrafficLights);
        dbClient.saveCarInfluence(car, concernedTrafficLights);

        /*if (!trafficLightToCurrentCars.containsKey(trafficLight)) {
            trafficLightToCurrentCars.put(trafficLight, new HashSet<>());
        }

        trafficLightToCurrentCars.get(trafficLight).add(car);*/
    }

    public void makeTrafficLightStopWaitCar(int trafficLight, int car) {
        //busyTrafficLights.remove(car);
        dbClient.removeCarInfluence(car);

        //trafficLightToCurrentCars.get(trafficLight).remove(car);
    }

    public boolean trafficLightIsWaiting(int trafficLight) {
        return dbClient.isTrafficLightInfluenced(trafficLight);
        //return trafficLightToCurrentCars.containsKey(trafficLight) && !trafficLightToCurrentCars.get(trafficLight).isEmpty();
    }

    private void updateCarRoute(int trafficLight, int carId) {
        List<Integer> route = dbClient.retrieveCarRoute(carId);
        //List<Integer> route = carToRoute.get(carId);

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
        //return knownCars.get(carId);
    }

    /*public Optional<Integer> nextTrafficLight(int carId) {
        List<Integer> trafficLights = dbClient.retrieveCarRoute(carId);
        //List<Integer> trafficLights = carToRoute.get(carId);

        if (trafficLights.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(trafficLights.get(0));
    }

    public Optional<CarQuery> nextQuery() {
        if (queries.isEmpty()) {
            return Optional.empty();
        }

        Collections.sort(queries);

        Iterator<CarQuery> iterator = queries.iterator();
        while (iterator.hasNext()) {
            CarQuery query = iterator.next();

            if (!isBusyIntersection(query.getTrafficLightId())) {
                iterator.remove();
                return Optional.of(query);
            }
        }

        return Optional.empty();
    }

    public List<CarQuery> popQueriesForTrafficLight(int trafficLight) {
        List<CarQuery> results = new ArrayList<>();

        Iterator<CarQuery> iterator = queries.iterator();
        while (iterator.hasNext()) {
            CarQuery query = iterator.next();

            if (query.getTrafficLightId() == trafficLight) {
                iterator.remove();
                results.add(query);
            }
        }

        return results;
    }*/

    public boolean isBusyIntersection(int trafficLight) {
        //return busyTrafficLights.values().stream().flatMap(Collection::stream).anyMatch(i -> i == trafficLight);
        return dbClient.isTrafficLightInfluenced(trafficLight);
    }

    /*public void addPendingRequest(int trafficLight, int carId) {
        if (pendingRequests.containsKey(trafficLight)) {
            //pendingRequests.get(trafficLight).add(dbClient.retrieveCar(carId));
            pendingRequests.get(trafficLight).add(knownCars.get(carId));
            pendingRequests.get(trafficLight).sort(Comparator.comparingInt(KnownCar::getPriority));

        } else {
            List<KnownCar> cars = new ArrayList<>();
            cars.add(dbClient.retrieveCar(carId));
            //cars.add(knownCars.get(carId));
            pendingRequests.put(trafficLight, cars);
            pendingRequests.get(trafficLight).sort(Comparator.comparingInt(KnownCar::getPriority));
        }
    }*/

    public void addPendingRequest(int carId, int priority, int trafficLightId) {
        dbClient.saveRequest(carId, priority, trafficLightId);
    }

    public boolean isCarWaitingAt(int trafficLight) {
        return dbClient.isPendingRequests(trafficLight);
        //return pendingRequests.containsKey(trafficLight) && !pendingRequests.get(trafficLight).isEmpty();
    }

    public int removePendingRequest(int trafficLight) {
        int nextPriorityCarId = dbClient.getHighestPriorityRequest(trafficLight);
        dbClient.removePendingRequest(nextPriorityCarId, trafficLight);
        /*List<KnownCar> carsWaitingList = pendingRequests.get(trafficLight);
        int nextPriorityCarId = carsWaitingList.remove(carsWaitingList.size() - 1).getId();

        if(carsWaitingList.isEmpty()){
            pendingRequests.remove(trafficLight);
        }*/
        return  nextPriorityCarId;
    }

    /*public void addQuery(int trafficLight, int carId) {
        CarQuery newQuery = new CarQuery(dbClient.retrieveCar(carId), trafficLight);
        //CarQuery newQuery = new CarQuery(knownCars.get(carId), trafficLight);
        for (CarQuery query : queries) {
            if (query.equals(newQuery)) {
                return;
            }
        }
        queries.add(newQuery);
    }*/
}
