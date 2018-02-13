package fr.polytech.al.five.business;

public class Position {

    private float longitude;
    private float latitude;

    public Position(float longitude, float latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public float getLatitude() {
        return latitude;
    }
}
