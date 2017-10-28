package fr.polytech.al.five.entities;

/**
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
public enum TrafficInformation {
    // TODO: To be defined.
    JAM("jam"), FREE("free"), UNKNOWN("unknow");

    private String name;

    TrafficInformation(String name) {
        this.name = name;
    }

    /**
     * Get name
     * @return
     */
    public String getName() {
        return this.name;
    }

    /**
     *
     * @param name
     * @return a role from its name
     */
    public static TrafficInformation fromName(String name) {
        for (TrafficInformation info : TrafficInformation.values()) {
            if (info.name.equals(name)) {
                return info;
            }
        }
        return UNKNOWN;
    }
}
