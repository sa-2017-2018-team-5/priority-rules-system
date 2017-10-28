package fr.polytech.al.five.model;

public class TrafficGroup {

    private String id;
    private int trafficLight;

    public TrafficGroup() {

    }

    public TrafficGroup(String id, int trafficLight) {
        this.id = id;
        this.trafficLight = trafficLight;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTrafficLight() {
        return trafficLight;
    }

    public void setTrafficLight(int trafficLight) {
        this.trafficLight = trafficLight;
    }

}
