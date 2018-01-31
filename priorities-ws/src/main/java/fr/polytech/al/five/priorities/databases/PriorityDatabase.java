package fr.polytech.al.five.priorities.databases;

import fr.polytech.al.five.priorities.business.CarType;

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
        System.out.println(newType.name);
        return true;
    }
}
