package fr.polytech.al.five.entities;

public class TrafficLight {

    private Integer id;
    private Position position;

    public TrafficLight(Integer id, Position position) {
        this.id = id;
        this.position = position;
    }

    public Integer getId() {
        return id;
    }

    public Position getPosition() {
        return position;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
