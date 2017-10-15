package fr.polytech.al.five.entities;

import java.util.Date;
import java.util.List;

public class Route {

    private Integer id;
    private List<String> instructions;
    private List<TrafficLight> encounteredLights;
    private Date departure;
}
