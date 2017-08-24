package org.cqrs.xapi.domain;

import org.cqrs.xapi.domain.test.DBTestCase;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class StatementTest  extends DBTestCase {

    @Test
    public void givenTestStatement_whenSearch_thenFindOne(){
        Statement existingStatement = entityManager.find(Statement.class, 1L);
        assertNotNull(existingStatement);
    }

    @Test
    public void saveStatementTest(){
        entityManager.getTransaction().begin();
        Statement testStatement = new Statement();
        testStatement.setName("new test statement");
        entityManager.persist(testStatement);
        entityManager.getTransaction().commit();

        List<Statement> statements = entityManager
                .createNamedQuery("getStatements", Statement.class)
                .getResultList();

        assertNotNull(statements);
        assertEquals(2, statements.size());
    }
}
