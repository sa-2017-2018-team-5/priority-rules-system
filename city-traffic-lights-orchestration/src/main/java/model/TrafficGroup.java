package model;

public class TrafficGroup {

    private String id;
    private String trafficLight;

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
}
