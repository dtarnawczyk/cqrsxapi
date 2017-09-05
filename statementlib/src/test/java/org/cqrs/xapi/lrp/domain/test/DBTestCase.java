package org.cqrs.xapi.lrp.domain.test;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.SQLException;

public class DBTestCase {

    protected static EntityManagerFactory factory;
    protected static EntityManager entityManager;

    @BeforeClass
    public static void initEntityManager() throws SQLException {
        factory = Persistence.createEntityManagerFactory("STATEMENT");
        entityManager = factory.createEntityManager();
    }

    @AfterClass
    public static void closeEntityManager() {
        entityManager.clear();
        entityManager.close();
        factory.close();
    }
}
