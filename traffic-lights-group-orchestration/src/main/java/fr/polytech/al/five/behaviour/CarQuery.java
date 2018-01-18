package fr.polytech.al.five.behaviour;

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
    public int compareTo(CarQuery carQuery) {
        if (car.isEmergency()) {
            if (carQuery.car.isEmergency()) {
                return car.getPriority() - carQuery.car.getPriority();
            } else {
                return 1;
            }
        } else {
            if (carQuery.car.isEmergency()) {
                return -1;
            } else {
                return car.getPriority() - carQuery.car.getPriority();
            }
        }
    }
}
