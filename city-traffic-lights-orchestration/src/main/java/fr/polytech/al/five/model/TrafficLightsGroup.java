package fr.polytech.al.five.model;

public class TrafficLightsGroup {

    private String id;
    private int trafficLight;

    public TrafficLightsGroup(String id, int trafficLight) {
        this.id = id;
        this.trafficLight = trafficLight;
    }

    public String getId() {
        return id;
    }

    public int getTrafficLight() {
        return trafficLight;
    }
}
