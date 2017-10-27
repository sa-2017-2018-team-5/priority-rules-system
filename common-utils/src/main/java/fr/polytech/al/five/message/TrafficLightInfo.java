package fr.polytech.al.five.message;

import java.io.Serializable;

public class TrafficLightInfo implements Serializable {

    private Integer id;

    public TrafficLightInfo() {
    }

    public TrafficLightInfo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
