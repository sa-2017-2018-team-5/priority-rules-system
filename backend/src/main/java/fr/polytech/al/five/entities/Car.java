package fr.polytech.al.five.entities;

public class Car {

    private Integer id;
    private Position currentPosition;
    private CarType type;

    public Car() {

    }

    public Car(Integer id, Position currentPosition, CarType type) {
        this.id = id;
        this.currentPosition = currentPosition;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public Position getCurrentPosition() {
        return currentPosition;
    }

    public CarType getType() {
        return type;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCurrentPosition(Position currentPosition) {
        this.currentPosition = currentPosition;
    }

    public void setType(CarType type) {
        this.type = type;
    }
}
