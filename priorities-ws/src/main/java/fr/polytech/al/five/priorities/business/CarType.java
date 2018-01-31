package fr.polytech.al.five.priorities.business;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
@XmlRootElement(name = "car-type")
public class CarType {

    @XmlElement
    public String name;

    @XmlElement
    public Integer priority;

    @XmlElement
    public CarStatus status;

    public CarType(String name, Integer priority, CarStatus status) {
        this.name = name;
        this.priority = priority;
        this.status = status;
    }

    public CarType() {
        // Nothing in this constructor.
    }
}
