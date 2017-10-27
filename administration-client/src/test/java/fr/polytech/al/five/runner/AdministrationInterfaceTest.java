package fr.polytech.al.five.runner;

import org.junit.Before;
import org.junit.Test;
import stubs.administration.AlreadyExistingCarType_Exception;
import stubs.administration.CarStatus;
import stubs.administration.CarType;
import stubs.administration.NotExistingCarType_Exception;
import utils.FalseAdministrationWebService;

import static org.junit.Assert.*;

/**
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
public class AdministrationInterfaceTest {

    private AdministrationInterface administrationInterface;
    private FalseAdministrationWebService falseAdministrationWebService;

    @Before
    public void initialize() {
        falseAdministrationWebService = new FalseAdministrationWebService();
        administrationInterface = new AdministrationInterface(falseAdministrationWebService);
    }

    @Test
    public void shouldCreateAType() throws NotExistingCarType_Exception {
        administrationInterface.create("TEST", 100, "EMERGENCY");

        assertEquals(1, falseAdministrationWebService.getRegisterPriorityCount());
        assertEquals(0, falseAdministrationWebService.getAlreadyExistingCarCount());

        CarType carType = falseAdministrationWebService.findPriorityByName("TEST");

        assertEquals("TEST", carType.getName());
        assertEquals(100, (int) carType.getPriority());
        assertEquals(CarStatus.EMERGENCY, carType.getStatus());
    }

    @Test
    public void shouldFailToCreateAType() throws AlreadyExistingCarType_Exception {
        CarType toBeRegistered = new CarType();
        toBeRegistered.setName("TEST");
        toBeRegistered.setPriority(100);
        toBeRegistered.setStatus(CarStatus.EMERGENCY);
        falseAdministrationWebService.registerPriority(toBeRegistered);

        administrationInterface.create("TEST", 100, "EMERGENCY");

        assertEquals(2, falseAdministrationWebService.getRegisterPriorityCount());
        assertEquals(1, falseAdministrationWebService.getAlreadyExistingCarCount());
    }

    @Test
    public void shouldModifyAType() throws NotExistingCarType_Exception, AlreadyExistingCarType_Exception {
        CarType toBeRegistered = new CarType();
        toBeRegistered.setName("TEST");
        toBeRegistered.setPriority(100);
        toBeRegistered.setStatus(CarStatus.EMERGENCY);
        falseAdministrationWebService.registerPriority(toBeRegistered);

        administrationInterface.update("TEST", 120);

        assertEquals(1, falseAdministrationWebService.getModifyPriorityCount());
        assertEquals(0, falseAdministrationWebService.getNotExistingCarCount());

        CarType carType = falseAdministrationWebService.findPriorityByName("TEST");

        assertEquals("TEST", carType.getName());
        assertEquals(120, (int) carType.getPriority());
        assertEquals(CarStatus.EMERGENCY, carType.getStatus());
    }

    @Test
    public void shouldFailToModifyNotExistingType() {
        administrationInterface.update("TEST", 120);

        assertEquals(1, falseAdministrationWebService.getModifyPriorityCount());
        assertEquals(1, falseAdministrationWebService.getNotExistingCarCount());
    }

    @Test
    public void shouldFetchType() throws AlreadyExistingCarType_Exception {
        CarType toBeRegistered = new CarType();
        toBeRegistered.setName("TEST");
        toBeRegistered.setPriority(100);
        toBeRegistered.setStatus(CarStatus.EMERGENCY);
        falseAdministrationWebService.registerPriority(toBeRegistered);

        administrationInterface.fetch("TEST");

        assertEquals(1, falseAdministrationWebService.getFindPriorityByNameCount());
        assertEquals(0, falseAdministrationWebService.getNotExistingCarCount());
    }

    @Test
    public void shouldFailToFetchType() {
        administrationInterface.fetch("TEST");

        assertEquals(1, falseAdministrationWebService.getFindPriorityByNameCount());
        assertEquals(1, falseAdministrationWebService.getNotExistingCarCount());
    }

    @Test
    public void shouldListAll() throws AlreadyExistingCarType_Exception {
        CarType toBeRegistered = new CarType();
        toBeRegistered.setName("TEST");
        toBeRegistered.setPriority(100);
        toBeRegistered.setStatus(CarStatus.EMERGENCY);
        falseAdministrationWebService.registerPriority(toBeRegistered);

        administrationInterface.listAll();

        assertEquals(1, falseAdministrationWebService.getFindAllPrioritiesCount());
    }
}