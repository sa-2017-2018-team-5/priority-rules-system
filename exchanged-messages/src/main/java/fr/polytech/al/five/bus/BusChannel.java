package fr.polytech.al.five.bus;

/**
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
public enum BusChannel {
    // Pub Sub channel
    ROUTE_PLANNED,
    TRAFFIC_LIGHT_OBSERVATION,
    TRAFFIC_LIGHTS_ORDER,
    TRAFFIC_LIGHT_STATUS,
    SUPERVISION,
    // Task channel
    ROUTE_PLANNED_TASK,
    TRAFFIC_LIGHT_OBSERVATION_TASK,
}
