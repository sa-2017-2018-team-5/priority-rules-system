package fr.polytech.al.five;

//import javax.ejb.Local;
import fr.polytech.al.five.entities.CarType;


//@Local
public interface PriorityReading {

    void getPriority(CarType uncompletedType);
}