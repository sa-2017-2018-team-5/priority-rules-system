package fr.polytech.al.five.routes.business;

public class Position {

    private long longitude;
    private long latitude;

    public Position(long longitude, long latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public long getLongitude() {
        return longitude;
    }

    public long getLatitude() {
        return latitude;
    }
}
