package org.cqrs.xapi.domain.test;

import org.cqrs.xapi.domain.Statement;
import org.h2.tools.RunScript;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
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
