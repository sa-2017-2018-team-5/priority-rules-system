package fr.polytech.al.five.behaviour;

import java.util.Objects;

public class CarQuery {

    private final int carId;
    private final int trafficLightId;
    private final int trafficLightGroupId;

    public CarQuery(int carId, int trafficLightId, int trafficLightGroupId) {
        this.carId = carId;
        this.trafficLightId = trafficLightId;
        this.trafficLightGroupId = trafficLightGroupId;
    }

    public int getCarId() {
        return carId;
    }

    public int getTrafficLightId() {
        return trafficLightId;
    }

    public int getTrafficLightGroupId() {
        return trafficLightGroupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarQuery carQuery = (CarQuery) o;
        return trafficLightId == carQuery.trafficLightId &&
                trafficLightGroupId == carQuery.trafficLightGroupId &&
                carId == carQuery.carId;
    }

    @Override
    public int hashCode() { return Objects.hash(carId, trafficLightId, trafficLightGroupId); }

    /*@Override
    public int compareTo(CarQuery carQuery) {
        // This sorts in descending order.
        if (car.isEmergency()) {
            if (carQuery.car.isEmergency()) {
                return carQuery.car.getPriority() - car.getPriority();
            } else {
                return -1;
            }
        } else {
            if (carQuery.car.isEmergency()) {
                return 1;
            } else {
                return carQuery.car.getPriority() - car.getPriority();
            }
        }
    }*/
}
