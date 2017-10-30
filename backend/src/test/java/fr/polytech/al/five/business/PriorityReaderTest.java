package fr.polytech.al.five.business;

import arquillian.AbstractPRSTest;
import fr.polytech.al.five.PriorityReader;
import fr.polytech.al.five.PriorityRegisterer;
import fr.polytech.al.five.entities.CarStatus;
import fr.polytech.al.five.entities.CarType;
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
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Antoine Aubé (aube.antoine@protonmail.com)
 */
@RunWith(Arquillian.class)
@Transactional(TransactionMode.COMMIT)
public class PriorityReaderTest extends AbstractPRSTest {

    @EJB
    private PriorityReader priorityReader;

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private UserTransaction userTransaction;

    private CarType firefighters;
    private CarType policemen;

    @Before
    public void setup() {
        firefighters = new CarType("FIREFIGHTERS", 100, CarStatus.EMERGENCY);
        policemen = new CarType("POLICEMEN", 90, CarStatus.EMERGENCY);

        entityManager.persist(firefighters);
        entityManager.persist(policemen);
    }

    @Test
    public void shouldFindFirefighters() {
        Optional<CarType> carType = priorityReader.getPriority("FIREFIGHTERS");

        assertTrue(carType.isPresent());
        assertEquals(firefighters, carType.get());
    }

    @Test
    public void shouldNotFindGreenCars() {
        Optional<CarType> carType = priorityReader.getPriority("GREEN_CARS");

        assertFalse(carType.isPresent());
    }

    @Test
    public void shouldFindAllCarTypes() {
        List<CarType> carTypes = priorityReader.getPriorities();

        assertEquals(2, carTypes.size());
    }

    @After
    public void cleanup() throws Exception {
        userTransaction.begin();

        firefighters = entityManager.merge(firefighters);
        entityManager.remove(firefighters);
        firefighters = null;

        policemen = entityManager.merge(policemen);
        entityManager.remove(policemen);
        policemen = null;

        userTransaction.commit();
    }
}
