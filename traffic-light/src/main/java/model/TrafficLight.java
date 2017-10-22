package model;

public class TrafficLight {

    private String id;
    private TrafficLightColour colour;

    public TrafficLight(String id, TrafficLightColour colour){
        this.id = id;
        this.colour = colour;
    }

    public TrafficLight(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TrafficLightColour getColour() {
        return colour;
    }

    public void setColour(TrafficLightColour colour) {
        this.colour = colour;
    }
}
