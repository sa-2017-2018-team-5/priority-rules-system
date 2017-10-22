package fr.polytech.al.five;

import javax.ejb.Local;
import fr.polytech.al.five.entities.CarType;

import java.util.Optional;

@Local
public interface PriorityReader {

    /**
     * Search a car type in the register.
     * @param uncompletedType At least the name of the car type for identification purpose.
     * @return The complete car type.
     */
    Optional<CarType> getPriority(CarType uncompletedType);
}