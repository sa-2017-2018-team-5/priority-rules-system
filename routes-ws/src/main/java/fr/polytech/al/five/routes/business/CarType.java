package fr.polytech.al.five.routes.business;

import javax.persistence.*;

/**
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
@Entity
@Table(name = "car_type")
public class CarType {

    private String name;
    private Integer priority;
    private CarStatus status;

    public CarType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public CarStatus getStatus() {
        return status;
    }

    public void setStatus(CarStatus status) {
        this.status = status;
    }
}
