package fr.polytech.al.five.persistence;

import arquillian.AbstractPRSTest;
import fr.polytech.al.five.entities.CarStatus;
import fr.polytech.al.five.entities.CarType;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
@Transactional(TransactionMode.COMMIT)
public class CarTypeStorageTest extends AbstractPRSTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void storingCarType() {
        CarType carType = new CarType("FIREFIGHTERS", 100, CarStatus.EMERGENCY);

        entityManager.persist(carType);
        CarType stored = entityManager.find(CarType.class, "FIREFIGHTERS");

        assertEquals(carType, stored);

        entityManager.remove(carType);
    }
}
