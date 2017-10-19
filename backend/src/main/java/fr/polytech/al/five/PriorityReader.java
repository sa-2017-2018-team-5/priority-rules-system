package fr.polytech.al.five;

import javax.ejb.Local;
import fr.polytech.al.five.entities.CarType;

import java.util.Optional;

@Local
public interface PriorityReader {

    Optional<CarType> getPriority(CarType uncompletedType);
}