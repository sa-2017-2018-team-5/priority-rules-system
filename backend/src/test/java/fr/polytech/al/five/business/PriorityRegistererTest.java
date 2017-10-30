package fr.polytech.al.five.business;

import arquillian.AbstractPRSTest;
import fr.polytech.al.five.PriorityRegisterer;
import fr.polytech.al.five.entities.CarStatus;
import fr.polytech.al.five.entities.CarType;
import fr.polytech.al.five.exceptions.AlreadyExistingCarType;
import fr.polytech.al.five.exceptions.NotExistingCarType;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import static org.junit.Assert.assertEquals;

/**
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
@RunWith(Arquillian.class)
@Transactional(TransactionMode.COMMIT)
public class PriorityRegistererTest extends AbstractPRSTest {

    @EJB
    private PriorityRegisterer priorityRegisterer;

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private UserTransaction userTransaction;

    private CarType firefighters;

    @Before
    public void setup() {
        firefighters = new CarType("FIREFIGHTERS", 100, CarStatus.EMERGENCY);
    }

    @After
    public void cleanup() throws Exception {
        userTransaction.begin();

        firefighters = entityManager.merge(firefighters);
        entityManager.remove(firefighters);
        firefighters = null;

        userTransaction.commit();
    }

    @Test
    public void shouldRegisterFirefighters() throws AlreadyExistingCarType {
        priorityRegisterer.registerPriority(firefighters);

        CarType carType = entityManager.find(CarType.class, "FIREFIGHTERS");

        assertEquals(firefighters, carType);
    }

    @Test(expected = AlreadyExistingCarType.class)
    public void shouldNotRegisterFirefightersTwice() throws AlreadyExistingCarType {
        priorityRegisterer.registerPriority(firefighters);

        CarType carType = new CarType("FIREFIGHTERS", 100, CarStatus.EMERGENCY);
        priorityRegisterer.registerPriority(carType);
    }

    @Test
    public void shouldModifyFirefighters() throws AlreadyExistingCarType, NotExistingCarType {
        priorityRegisterer.registerPriority(firefighters);

        CarType carType = new CarType();
        carType.setName("FIREFIGHTERS");

        priorityRegisterer.modifyPriority(carType, 120);

        carType = entityManager.find(CarType.class, "FIREFIGHTERS");

        assertEquals(120, (int) carType.getPriority());
    }

    @Test(expected = NotExistingCarType.class)
    public void shouldNotModifyNotExistingType() throws NotExistingCarType {
        CarType carType = new CarType();
        carType.setName("FIREFIGHTERS");

        priorityRegisterer.modifyPriority(carType, 120);
    }
}
