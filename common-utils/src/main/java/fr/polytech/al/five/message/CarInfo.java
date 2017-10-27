package fr.polytech.al.five.message;

import java.io.Serializable;

public class CarInfo implements Serializable {

    private Integer id;
    private String name;
    private Integer priority;
    private String CarStatus;

    public CarInfo() {
    }

    public CarInfo(Integer id, String name, Integer priority, String carStatus) {
        this.id = id;
        this.name = name;
        this.priority = priority;
        CarStatus = carStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getCarStatus() {
        return CarStatus;
    }

    public void setCarStatus(String carStatus) {
        CarStatus = carStatus;
    }
}
