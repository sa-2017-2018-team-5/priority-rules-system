package fr.polytech.al.five.routes.components;

import fr.polytech.al.five.routes.business.CarType;

import javax.ejb.Local;
import java.util.Optional;

@Local
public interface PriorityChecker {

    Optional<CarType> checkPriority(String priorityName);
}
