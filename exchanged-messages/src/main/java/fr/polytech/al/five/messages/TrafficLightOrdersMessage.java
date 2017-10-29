package fr.polytech.al.five.messages;

import java.util.List;

/**
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
public class TrafficLightOrdersMessage extends Message {

    private final List<Integer> mustBecomeRed;
    private final List<Integer> mustBecomeGreen;

    public TrafficLightOrdersMessage(List<Integer> mustBecomeRed, List<Integer> mustBecomeGreen) {
        this.mustBecomeRed = mustBecomeRed;
        this.mustBecomeGreen = mustBecomeGreen;
    }

    public List<Integer> getMustBecomeRed() {
        return mustBecomeRed;
    }

    public List<Integer> getMustBecomeGreen() {
        return mustBecomeGreen;
    }
}
