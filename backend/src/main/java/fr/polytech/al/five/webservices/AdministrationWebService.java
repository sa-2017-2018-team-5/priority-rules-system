package fr.polytech.al.five.webservices;

import fr.polytech.al.five.entities.CarType;
import fr.polytech.al.five.exceptions.AlreadyExistingCarType;
import fr.polytech.al.five.exceptions.NotExistingCarType;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

/**
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
@WebService(targetNamespace = "http://www.polytech.fr/al/five/administration")
public interface AdministrationWebService {

    /**
     * Registers a new car priority type.
     * @param carType Descriptor of the car type.
     */
    @WebMethod
    void registerPriority(CarType carType) throws AlreadyExistingCarType;

    /**
     * Modifies the details of a car priority type.
     * @param carType Modified descriptor of the car type.
     */
    @WebMethod
    void modifyPriority(CarType carType) throws NotExistingCarType;

    /**
     * Retrieves a car type by its name.
     * @param typeName The name of the car type.
     * @return A car type or nothing.
     */
    @WebMethod
    CarType findPriorityByName(String typeName) throws NotExistingCarType;

    /**
     * Retrieves all registered car types.
     * @return The list of registered car types.
     */
    @WebMethod
    List<CarType> findAllPriorities();
}
