package fr.polytech.al.five.behaviour;

import fr.polytech.al.five.messages.contents.LightStatus;

import java.util.HashMap;
import java.util.Map;

public class SupervisionState {

    private final Map<Integer, LightStatus> trafficLightsStatus;

    public SupervisionState() {
        trafficLightsStatus = new HashMap<>();
    }

    public void updateTrafficLight(int trafficLightId, LightStatus currentLightStatus) {
        trafficLightsStatus.put(trafficLightId, currentLightStatus);
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();

        trafficLightsStatus.forEach((id, status) ->
                builder.append("Traffic light #").append(id).append(" has status: ").append(status).append("\n"));
        
        return builder.toString();
    }
}
