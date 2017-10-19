package fr.polytech.al.five;

import javax.ejb.Local;
import fr.polytech.al.five.entities.CarType;

@Local
public interface PriorityRegisterer {

    void registerPriority(CarType type);
    void modifyPriority(CarType type, Integer priority);
}