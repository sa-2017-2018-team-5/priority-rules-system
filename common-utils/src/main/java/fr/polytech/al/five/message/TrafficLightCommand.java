package fr.polytech.al.five.message;

import java.io.Serializable;

public class TrafficLightCommand implements Serializable {

    private TrafficLightInfo trafficLightInfo;
    private TrafficLightColour colour;

    public TrafficLightCommand() {
    }

    public TrafficLightCommand(TrafficLightInfo trafficLightInfo, TrafficLightColour colour) {
        this.trafficLightInfo = trafficLightInfo;
        this.colour = colour;
    }

    public TrafficLightInfo getTrafficLightInfo() {
        return trafficLightInfo;
    }

    public void setTrafficLightInfo(TrafficLightInfo trafficLightInfo) {
        this.trafficLightInfo = trafficLightInfo;
    }

    public TrafficLightColour getColour() {
        return colour;
    }

    public void setColour(TrafficLightColour colour) {
        this.colour = colour;
    }

    @Override
    public String toString() {
        return "TrafficLightCommand{" +
                "trafficLightInfo=" + trafficLightInfo +
                ", colour=" + colour +
                '}';
    }
}
