package fr.polytech.al.five.message;

import java.io.Serializable;

public class CarDetection implements Serializable{

    private TrafficLightInfo trafficLightInfo;
    private CarInfo carInfo;
    private CarStatus carStatus;

    public CarDetection() {
    }

    public CarDetection(TrafficLightInfo trafficLightInfo, CarInfo carInfo, CarStatus carStatus) {
        this.trafficLightInfo = trafficLightInfo;
        this.carInfo = carInfo;
        this.carStatus = carStatus;
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

    public CarStatus getCarStatus() {
        return carStatus;
    }

    public void setCarStatus(CarStatus carStatus) {
        this.carStatus = carStatus;
    }

    @Override
    public String toString() {
        return "CarDetection{" +
                "trafficLightInfo=" + trafficLightInfo +
                ", carInfo=" + carInfo +
                ", carStatus=" + carStatus +
                '}';
    }
}
