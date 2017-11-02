package fr.polytech.al.five.messages;

import fr.polytech.al.five.messages.contents.LightStatus;

import java.util.List;

/**
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
public class TrafficLightOrdersMessage implements Message {

    private final List<Integer> mustBecomeRed;
    private final List<Integer> mustBecomeGreen;
    private final LightStatus trafficLightStatus;

    public TrafficLightOrdersMessage(List<Integer> mustBecomeRed, List<Integer> mustBecomeGreen, LightStatus status) {
        this.mustBecomeRed = mustBecomeRed;
        this.mustBecomeGreen = mustBecomeGreen;
        this.trafficLightStatus = status;
    }

    public List<Integer> getMustBecomeRed() {
        return mustBecomeRed;
    }

    public List<Integer> getMustBecomeGreen() {
        return mustBecomeGreen;
    }

    public LightStatus getTrafficLightStatus() { return trafficLightStatus; }
}
