package fr.polytech.al.five.entities;

public class CarType {

    private String name;
    private Integer priority;

    public CarType() {
        this.name = "DEFAULT_CAR_TYPE";
        this.priority = 0;
    }

    public CarType(String name, Integer priority) {
        this.name = name;
        this.priority = priority;
    }

    public String getName() {
        return name;
    }
}
