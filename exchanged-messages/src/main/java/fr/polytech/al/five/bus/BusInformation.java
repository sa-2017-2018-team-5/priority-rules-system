package fr.polytech.al.five.bus;

/**
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
public class BusInformation {

    private final String hostname;

    public BusInformation(String hostname) {
        this.hostname = hostname;
    }

    public String getHostname() {
        return hostname;
    }
}
