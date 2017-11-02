package fr.polytech.al.five.messages;

import fr.polytech.al.five.messages.contents.LightStatus;

public class TrafficLightStatusMessage implements Message {

    private final int trafficLightId;

    private final LightStatus status;

    public TrafficLightStatusMessage(int trafficLightId, LightStatus status) {
        this.trafficLightId = trafficLightId;
        this.status = status;
    }

    public int getTrafficLightId() {
        return trafficLightId;
    }

    public LightStatus getLightStatus() {
        return status;
    }
}
