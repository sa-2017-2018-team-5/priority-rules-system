package fr.polytech.al.five.behaviour;

import java.util.Objects;

public class CarQuery implements Comparable<CarQuery> {

    private final KnownCar car;
    private final int trafficLightId;

    public CarQuery(KnownCar car, int trafficLightId) {
        this.car = car;
        this.trafficLightId = trafficLightId;
    }

    public KnownCar getCar() {
        return car;
    }

    public int getTrafficLightId() {
        return trafficLightId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarQuery carQuery = (CarQuery) o;
        return trafficLightId == carQuery.trafficLightId &&
                Objects.equals(car, carQuery.car);
    }

    @Override
    public int hashCode() {
        return Objects.hash(car, trafficLightId);
    }

    @Override
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
    }
}
