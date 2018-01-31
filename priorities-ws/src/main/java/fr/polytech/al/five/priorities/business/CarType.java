package fr.polytech.al.five.priorities.business;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
@Entity
@Table(name = "car_type")
@XmlRootElement(name = "car-type")
public class CarType {

    @Id
    private String name;

    private Integer priority;

    @Enumerated(EnumType.STRING)
    private CarStatus status;

    public CarType(String name, Integer priority, CarStatus status) {
        this.name = name;
        this.priority = priority;
        this.status = status;
    }

    public CarType() {
        // Used in (un)marshalling & for JPA.
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

    public CarStatus getStatus() {
        return status;
    }

    public void setStatus(CarStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CarType carType = (CarType) o;

        if (!name.equals(carType.name)) return false;
        if (!priority.equals(carType.priority)) return false;
        return status == carType.status;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + priority.hashCode();
        result = 31 * result + status.hashCode();
        return result;
    }
}
