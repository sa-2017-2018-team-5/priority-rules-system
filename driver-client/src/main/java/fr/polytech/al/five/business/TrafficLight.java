package fr.polytech.al.five.business;

/**
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
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
}
