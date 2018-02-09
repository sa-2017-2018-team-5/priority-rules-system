package fr.polytech.al.five.tlws.components;

import fr.polytech.al.five.messages.contents.CarAction;

import javax.ejb.Local;

@Local
public interface BusCommunication {

    void sendCarStatus(int trafficLight, int carId, CarAction status);
}
