package fr.polytech.al.five.priorities.business;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
@XmlRootElement
public class CarType {

    @XmlElement
    public String name;

    @XmlElement
    public Integer priority;

    @XmlElement
    public CarStatus status;
}
