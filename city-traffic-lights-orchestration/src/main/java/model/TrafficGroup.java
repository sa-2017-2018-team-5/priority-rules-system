package model;

import stubs.route.TrafficLight;

import java.util.List;

public class TrafficGroup {

    private String id;
    private String trafficLight;
    private List<TrafficLight> trafficLights;

    public TrafficGroup(){

    }

    public TrafficGroup(String id, String trafficLight) {
        this.id = id;
        this.trafficLight = trafficLight;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrafficLight() {
        return trafficLight;
    }

    public void setTrafficLight(String trafficLight) {
        this.trafficLight = trafficLight;
    }

    public List<TrafficLight> getTrafficLights() {
        return trafficLights;
    }

    public void setTrafficLights(List<TrafficLight> trafficLights) {
        this.trafficLights = trafficLights;
    }
}
