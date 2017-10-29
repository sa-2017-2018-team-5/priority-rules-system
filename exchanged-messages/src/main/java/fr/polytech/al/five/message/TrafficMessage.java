package fr.polytech.al.five.message;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class TrafficMessage implements Serializable{

    private CarInfo car;
    private List<TrafficLightInfo> trafficLights;
    private Date departure;

    public TrafficMessage() {
    }

    public TrafficMessage(CarInfo car, List<TrafficLightInfo> trafficLights, Date departure) {
        this.car = car;
        this.trafficLights = trafficLights;
        this.departure = departure;
    }

    public CarInfo getCar() {
        return car;
    }

    public void setCar(CarInfo car) {
        this.car = car;
    }

    public List<TrafficLightInfo> getTrafficLights() {
        return trafficLights;
    }

    public void setTrafficLights(List<TrafficLightInfo> trafficLights) {
        this.trafficLights = trafficLights;
    }

    public Date getDeparture() {
        return departure;
    }

    public void setDeparture(Date departure) {
        this.departure = departure;
    }

    @Override
    public String toString() {
        return "TrafficMessage{" +
                "car=" + car +
                ", trafficLights=" + trafficLights +
                ", departure=" + departure +
                '}';
    }
}
