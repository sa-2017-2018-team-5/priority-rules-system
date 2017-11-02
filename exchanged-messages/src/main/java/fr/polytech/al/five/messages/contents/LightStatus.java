package fr.polytech.al.five.messages.contents;

import java.io.Serializable;

public enum LightStatus implements Serializable{
    FORCED_GREEN,
    FORCED_RED,
    FORCED,
    NORMAL,
    WAITING_TO_TURN_GREEN;
}
