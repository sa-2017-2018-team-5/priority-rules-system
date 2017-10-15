package fr.polytech.al.five.webservices.implementations;

import fr.polytech.al.five.entities.CarType;
import fr.polytech.al.five.webservices.AdministrationWebService;
import org.apache.commons.lang.NotImplementedException;

import javax.ejb.Stateless;
import javax.jws.WebService;

@WebService
@Stateless(name = "AdministrationWS")
public class AdministrationWebServiceImplementation
        implements AdministrationWebService {

    @Override
    public void registerPriority(CarType carType) {
        throw new NotImplementedException();
    }

    @Override
    public void modifyPriority(CarType carType) {
        throw new NotImplementedException();
    }
}
