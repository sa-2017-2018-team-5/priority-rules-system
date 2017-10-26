package fr.polytech.al.five.entities;

/**
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
public class Position {

    private Float longitude;
    private Float latitude;

    public Position() {

    }

    public Position(Float longitude, Float latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }
}
