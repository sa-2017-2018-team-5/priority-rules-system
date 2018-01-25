package fr.polytech.al.five.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrafficGroup {
    private Integer groupId;
    private List<Integer> trafficID;
    private Map<Integer, List<Integer>> rules;

    public TrafficGroup() {
        trafficID = new ArrayList<>();
        rules = new HashMap<>();
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public List<Integer> getTrafficID() {
        return trafficID;
    }

    public void setTrafficID(List<Integer> trafficID) {
        this.trafficID = trafficID;
    }

    public Map<Integer, List<Integer>> getRules() {
        return rules;
    }

    public void setRules(Map<Integer, List<Integer>> rules) {
        this.rules = rules;
    }

    @Override
    public String toString() {
        return "TrafficGroup{" +
                "groupId=" + groupId.toString() +
                ", trafficID=" + trafficID.toString() +
                ", rules=" + rules.toString() +
                '}';
    }
}
