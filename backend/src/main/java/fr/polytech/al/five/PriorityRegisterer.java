package fr.polytech.al.five;

import javax.ejb.Local;
import fr.polytech.al.five.entities.CarType;

@Local
public interface PriorityRegisterer {

    /**
     * Add the given car type to the register.
     * @param type A car type with its priority.
     */
    void registerPriority(CarType type);

    /**
     * Update a car type.
     * @param type A car type.
     * @param priority The new priority.
     */
    void modifyPriority(CarType type, Integer priority);
}