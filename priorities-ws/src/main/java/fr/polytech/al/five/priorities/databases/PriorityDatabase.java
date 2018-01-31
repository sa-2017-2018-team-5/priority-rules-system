package fr.polytech.al.five.priorities.databases;

import fr.polytech.al.five.priorities.business.CarStatus;
import fr.polytech.al.five.priorities.business.CarType;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class PriorityDatabase {

    private static PriorityDatabase instance;

    public static PriorityDatabase getInstance() {
        if (instance == null) {
            instance = new PriorityDatabase();
        }

        return instance;
    }

    private PriorityDatabase() {
        // As a singleton, this should not be instantiated out this class.
    }

    public boolean register(CarType newType) {
        return true;
    }

    public Optional<CarType> find(String name) {
        if ("FIREFIGHTERS".equals(name)) {
            return Optional.of(new CarType("FIREFIGHTERS", 100, CarStatus.EMERGENCY));
        } else {
            return Optional.empty();
        }
    }

    public List<CarType> findAll() {
        return Arrays.asList(
                new CarType("FIREFIGHTERS", 100, CarStatus.EMERGENCY),
                new CarType("CARPOOLING", 20, CarStatus.PRIVILEGED));
    }
}
