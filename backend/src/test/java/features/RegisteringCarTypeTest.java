package features;

import arquillian.AbstractPRSTest;
import cucumber.api.CucumberOptions;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.runtime.arquillian.CukeSpace;
import fr.polytech.al.five.PriorityReader;
import fr.polytech.al.five.PriorityRegisterer;
import fr.polytech.al.five.entities.CarStatus;
import fr.polytech.al.five.entities.CarType;
import fr.polytech.al.five.exceptions.AlreadyExistingCarTypeException;
import fr.polytech.al.five.exceptions.NotExistingCarTypeException;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

@RunWith(CukeSpace.class)
@CucumberOptions(features = "src/test/resources")
public class RegisteringCarTypeTest extends AbstractPRSTest {

    @EJB
    private PriorityRegisterer priorityRegisterer;

    @EJB
    private PriorityReader priorityReader;

    private CarType carType;

    @Given("^an? (emergency|privileged) car type named (.*) which priority is (\\d+)$")
    public void registerCarType(String status, String name, int priority) throws AlreadyExistingCarTypeException {
        CarType newCarType = new CarType(name, priority, CarStatus.valueOf(status.toUpperCase()));

        priorityRegisterer.registerPriority(newCarType);
    }

    @When("^the car type (.*) is fetched$")
    public void fetchCarType(String name) {
        carType = priorityReader.getPriority(name).orElse(null);
    }

    @When("^the priority of the type (.*) is changed to (\\d+)")
    public void updateCarType(String name, int priority) throws NotExistingCarTypeException {
        CarType toUpdate = new CarType();
        toUpdate.setName(name);

        priorityRegisterer.modifyPriority(toUpdate, priority);
    }

    @Then("^the car type priority should be (\\d+)$")
    public void checkPriority(int expected) {
        if (carType == null) {
            fail();
        } else {
            assertEquals(expected, (int) carType.getPriority());
        }
    }

    @Then("^the cars of this type should be (emergency|privileged) ones")
    public void checkStatus(String expected) {
        if (carType == null) {
            fail();
        } else {
            assertEquals(CarStatus.valueOf(expected.toUpperCase()), carType.getStatus());
        }
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    UserTransaction utx;

    @cucumber.api.java.After
    public void cleaningUpContext() throws Exception {
        CarType emergency = new CarType("FIREFIGHTERS", null, null);
        CarType privileged = new CarType("GREEN_CAR", null, null);

        utx.begin();

        emergency = entityManager.merge(emergency);
        entityManager.remove(emergency);

        privileged = entityManager.merge(privileged);
        entityManager.remove(privileged);

        utx.commit();

        carType = null;
    }

}
