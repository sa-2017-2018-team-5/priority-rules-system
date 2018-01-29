package fr.polytech.al.five.data;

import com.mongodb.client.MongoCursor;
import fr.polytech.al.five.behaviour.CarQuery;
import fr.polytech.al.five.behaviour.KnownCar;
import org.apache.log4j.Logger;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class DBClient {

    private static final Logger LOGGER = Logger.getLogger(DBClient.class);
    private QueryHandler queryHandler;

    public DBClient() {
        queryHandler = new QueryHandler();
        boolean isConnected = queryHandler.initQueryHandler();
        if (isConnected) {
            LOGGER.info("Connected to requested database");
        } else {
            LOGGER.info("Connection failure");
        }
    }

    private boolean isCarRegistered(int carId) {
        MongoCursor<Document> carsInfosCursor = queryHandler.selectWhere("car-id", carId,
                                                                         "carsInfos");

        return carsInfosCursor != null && carsInfosCursor.hasNext();
    }

    public void saveCar(KnownCar car) {
        if (isCarRegistered(car.getId())) {
            return;
        }

        Document carInfos = new Document();
        carInfos.append("car-id", car.getId());
        carInfos.append("car-priority", car.getPriority());
        carInfos.append("emergency", car.isEmergency());

        queryHandler.insertDocumentIntoCollection(carInfos, "carsInfos");
    }

    public boolean isRouteDefinedForCar(int carId) {
        MongoCursor<Document> carsInfosCursor = queryHandler.selectWhere("car-id", carId,
                                                                         "plannedRoutes");

        return carsInfosCursor != null && carsInfosCursor.hasNext();
    }

    public void saveCarRoute(int carId, List<Integer> trafficLightsOnRoute) {
        Document itinerary = new Document();
        itinerary.append("car-id", carId);
        itinerary.append("tl-on-route", trafficLightsOnRoute);

        queryHandler.insertDocumentIntoCollection(itinerary, "plannedRoutes");
    }

    public KnownCar retrieveCar(int carId) {
        if (!isCarRegistered(carId)) {
            return null;
        }

        MongoCursor<Document> cursor = queryHandler.selectWhere("car-id", carId, "carsInfos");
        Document document = cursor.next();
        KnownCar knownCar = new KnownCar(document.getInteger("car-id"),
                                         document.getInteger("car-priority"),
                                         document.getBoolean("emergency"));
        return knownCar;
    }

    public List<Integer> retrieveCarRoute(int carId) {
        return queryHandler.selectArrayWhere("car-id", carId, "tl-on-route",
                                             "plannedRoutes");
    }

    public void saveRequest(int carId, int priority, int trafficLightGroupId, int trafficLightId) {
        Document itinerary = new Document();
        itinerary.append("car-id", carId);
        itinerary.append("priority", priority);
        itinerary.append("tl-group-id", trafficLightGroupId);
        itinerary.append("tl-id", trafficLightId);

        LOGGER.info("Creating pending request database");
        queryHandler.insertDocumentIntoCollection(itinerary, "pendingRequests");
    }

    public boolean isPendingRequests(int trafficLightGroupId) {
        MongoCursor<Document> pendingRequestsCursor = queryHandler.selectWhere("tl-group-id",
                                                                                trafficLightGroupId,
                                                                               "pendingRequests");

        return pendingRequestsCursor != null && pendingRequestsCursor.hasNext();
    }

    public void removePendingRequest(int carId, int trafficLightId) {
        List<String> attributesNames = new ArrayList<>();
        List<Object> attributesValues = new ArrayList<>();
        attributesNames.add("car-id");
        attributesNames.add("tl-id");
        attributesValues.add(carId);
        attributesValues.add(trafficLightId);
        queryHandler.remove(attributesNames, attributesValues, "pendingRequests");
    }

    public CarQuery getHighestPriorityRequest(int trafficLightGroupId) {
        MongoCursor<Document> sortedCollection = queryHandler.selectWhereSorted("tl-group-id",
                                                                                trafficLightGroupId,
                                                                                "priority", -1,
                                                                                "pendingRequests");
        Document document = sortedCollection.next();
        return new CarQuery(document.getInteger("car-id"),
                                      document.getInteger("tl-id"),
                                      document.getInteger("tl-group-id"));
    }

    public void updateRoute(int carId, List<Integer> updatedRoute) {
        queryHandler.update("car-id", carId, "tl-on-route", updatedRoute,
                            "plannedRoutes");
    }

    public void removeRoute(int carId)  {
        queryHandler.remove("car-id", carId, "plannedRoutes");
    }

    public void saveCarInfluence(int carId, List<Integer> trafficLights) {
        Document carInfluence = new Document();
        carInfluence.append("car-id", carId);
        carInfluence.append("tl-affected", trafficLights);

        queryHandler.insertDocumentIntoCollection(carInfluence, "influencedTrafficLights");
    }

    public boolean isTrafficLightInfluenced(int trafficLight) {
        return queryHandler.isInArray("tl-affected", trafficLight, "influencedTrafficLights");
    }

    public void removeCarInfluence(int carId) {
        // TODO see when to use this if car is out of its way
        queryHandler.remove("car-id", carId, "influencedTrafficLights");
    }
}
