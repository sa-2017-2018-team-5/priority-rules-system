package fr.polytech.al.five.message;

import java.io.Serializable;

public class CarDetection implements Serializable{

    private TrafficLightInfo trafficLightInfo;
    private CarInfo carInfo;
    private CarPosition carPosition;

    public CarDetection() {
    }

    public CarDetection(TrafficLightInfo trafficLightInfo, CarInfo carInfo, CarPosition carPosition) {
        this.trafficLightInfo = trafficLightInfo;
        this.carInfo = carInfo;
        this.carPosition = carPosition;
    }

    public TrafficLightInfo getTrafficLightInfo() {
        return trafficLightInfo;
    }

    public void setTrafficLightInfo(TrafficLightInfo trafficLightInfo) {
        this.trafficLightInfo = trafficLightInfo;
    }

    public CarInfo getCarInfo() {
        return carInfo;
    }

    public void setCarInfo(CarInfo carInfo) {
        this.carInfo = carInfo;
    }

    public CarPosition getCarPosition() {
        return carPosition;
    }

    public void setCarPosition(CarPosition carPosition) {
        this.carPosition = carPosition;
    }

    @Override
    public String toString() {
        return "CarDetection{" +
                "trafficLightInfo=" + trafficLightInfo +
                ", carInfo=" + carInfo +
                ", carPosition=" + carPosition +
                '}';
    }
}
