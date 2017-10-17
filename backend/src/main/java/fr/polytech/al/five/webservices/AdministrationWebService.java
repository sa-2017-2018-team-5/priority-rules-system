package fr.polytech.al.five.webservices;

import fr.polytech.al.five.entities.CarType;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(targetNamespace = "http://www.polytech.fr/al/five/administration")
public interface AdministrationWebService {

    @WebMethod
    void registerPriority(CarType carType);

    @WebMethod
    void modifyPriority(CarType carType);
}
