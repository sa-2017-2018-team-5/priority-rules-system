package fr.polytech.al.five.controller;

import fr.polytech.al.five.model.TrafficLight;
import fr.polytech.al.five.model.TrafficLightColour;

public class TrafficLightController {

    private TrafficLight trafficLight;

    private static final String ID = "City";

    public TrafficLightController(){
        this.trafficLight = new TrafficLight("myId", TrafficLightColour.RED);
    }

    /**
     * Switch the traffic light colour
     * @param colour the traffic light colour
     */
    public void switchColour(TrafficLightColour colour){
        this.trafficLight.setColour(colour);
        System.out.println("traffic light color : " + trafficLight.getColour().toString());
    }

}
