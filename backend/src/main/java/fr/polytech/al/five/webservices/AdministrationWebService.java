package fr.polytech.al.five.webservices;

import fr.polytech.al.five.entities.CarType;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(targetNamespace = "http://www.polytech.fr/al/five/administration")
public interface AdministrationWebService {

    /**
     * Registers a new car priority type.
     * @param carType Descriptor of the car type.
     */
    @WebMethod
    void registerPriority(CarType carType);

    /**
     * Modify the details of a car priority type.
     * @param carType Modified descriptor of the car type.
     */
    @WebMethod
    void modifyPriority(CarType carType);
}
