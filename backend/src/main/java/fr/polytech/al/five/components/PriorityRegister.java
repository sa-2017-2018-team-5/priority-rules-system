package fr.polytech.al.five.components;

import fr.polytech.al.five.PriorityReader;
import fr.polytech.al.five.PriorityRegisterer;
import fr.polytech.al.five.entities.CarType;

import javax.ejb.Stateless;
import java.util.Optional;

@Stateless
public class PriorityRegister implements PriorityRegisterer, PriorityReader {

    @Override
    public Optional<CarType> getPriority(CarType uncompletedType) {
        // TODO: Implement the database.
        CarType carType = new CarType(uncompletedType.getName(), 100);

        return Optional.of(carType);
    }

    @Override
    public void registerPriority(CarType type) {
        // Do nothing. TODO: Implement the database.
    }

    @Override
    public void modifyPriority(CarType type, Integer priority) {
        // Do nothing. TODO: Implement the database.
    }
}
