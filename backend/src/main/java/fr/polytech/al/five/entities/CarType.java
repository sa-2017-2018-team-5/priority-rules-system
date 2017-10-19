package fr.polytech.al.five.entities;

public class CarType {

    private String name;
    private Integer priority;

    public CarType() {

    }

    public CarType(String name, Integer priority) {
        this.name = name;
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }
}
