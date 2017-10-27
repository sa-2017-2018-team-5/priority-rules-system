package fr.polytech.al.five;

import javax.ejb.Local;
import fr.polytech.al.five.entities.CarType;

import java.util.List;
import java.util.Optional;

@Local
public interface PriorityReader {

    /**
     * Search a car type in the register.
     * @param typeName The name of the car type for identification purpose.
     * @return The complete car type.
     */
    Optional<CarType> getPriority(String typeName);

    /**
     * @return All the registered priorities.
     */
    List<CarType> getPriorities();
}