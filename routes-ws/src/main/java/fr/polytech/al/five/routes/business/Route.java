package fr.polytech.al.five.routes.business;

import java.util.Date;
import java.util.List;

/**
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
public class Route {

    private Integer id;
    private List<String> instructions;
    private List<TrafficLight> encounteredLights;
    private Date departure;

    public Route(Integer id, List<String> instructions, List<TrafficLight> encounteredLights, Date departure) {
        this.id = id;
        this.instructions = instructions;
        this.encounteredLights = encounteredLights;
        this.departure = departure;
    }

    public Integer getId() {
        return id;
    }

    public List<String> getInstructions() {
        return instructions;
    }

    public List<TrafficLight> getEncounteredLights() {
        return encounteredLights;
    }

    public Date getDeparture() {
        return departure;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setInstructions(List<String> instructions) {
        this.instructions = instructions;
    }

    public void setEncounteredLights(List<TrafficLight> encounteredLights) {
        this.encounteredLights = encounteredLights;
    }

    public void setDeparture(Date departure) {
        this.departure = departure;
    }
}
