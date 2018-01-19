package fr.polytech.al.five.behaviour;

public class KnownCar {

    private final int id;
    private final int priority;
    private final boolean isEmergency;

    public KnownCar(int id, int priority, boolean isEmergency) {
        this.id = id;
        this.priority = priority;
        this.isEmergency = isEmergency;
    }

    public int getId() {
        return id;
    }

    public int getPriority() {
        return priority;
    }

    public boolean isEmergency() {
        return isEmergency;
    }
}
