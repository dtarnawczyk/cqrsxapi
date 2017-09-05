package org.cqrs.xapi.lrp.domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

public class StatementTest extends DBTestCase {

    // STATEMENT
    String statementId;
    Statement testStatement;

    // ACTOR
    String actorsName = "Test Actor";
    String actorsObjectType = "Agent";

    // IFI
    String mbox = "mailto:test@mail.com";
    String mbox_sha1sum = "444444444";
    String openId = "1234567";

    // ACCOUNT
    String homepage = "www.google.pl";
    String accountName = "test";

    // VERB
    String verbId = "Human readable verb";
    String displayName = "testowanie";
    String dispalyLanguage = "pl_PL";

    // OBJECT
    String objectType = "Activity";

    @Before
    public void saveStatement() {
        IFI inverseFunctionalIdentifier = new IFI();

        inverseFunctionalIdentifier.setMbox(mbox);
        inverseFunctionalIdentifier.setMbox_sha1sum(mbox_sha1sum);
        inverseFunctionalIdentifier.setOpenid(openId);

        Account testAccount = new Account(homepage, accountName);
        inverseFunctionalIdentifier.setAccount(testAccount);

        this.statementId = UUID.randomUUID().toString();
        this.testStatement = new Statement(statementId);

        Actor testActor = new Actor(testStatement);
        testActor.setName(actorsName);
        testActor.setObjectType(actorsObjectType);

        testActor.setInverseFunctionalIdentifier(inverseFunctionalIdentifier);

        testStatement.setActor(testActor);

        Verb verb = new Verb(testStatement);
        verb.setId(verbId);
        Map<String, String> displayMap = new HashMap<>();
        displayMap.put(dispalyLanguage, displayName);
        verb.setDisplay(displayMap);

        testStatement.setVerb(verb);

        XapiObject xapiObject = new XapiObject(testStatement);
        xapiObject.setObjectType(objectType);

        testStatement.setObject(xapiObject);

        entityManager.getTransaction().begin();
        entityManager.persist(testStatement);
        entityManager.getTransaction().commit();
    }

    @After
    public void removeStatement() {
        entityManager.remove(testStatement);
    }

    @Test
    public void shouldPersistStatement() {
        Statement savedStatement = entityManager.find(Statement.class, statementId);
        assertNotNull(savedStatement);
    }

    @Test
    public void getAllStatementsTest() {

        List<Statement> statements = entityManager
                .createNamedQuery("getStatements", Statement.class)
                .getResultList();
        assertNotNull(statements);
        assertEquals(1, statements.size());
    }

    @Test
    public void shouldPersistActor() {
        Statement savedStatement = entityManager.find(Statement.class, statementId);
        assertNotNull(savedStatement);

        Actor savedActor = savedStatement.getActor();
        assertNotNull(savedActor);

        assertEquals(actorsName, savedActor.getName());
        assertEquals(actorsObjectType, savedActor.getObjectType());

        IFI ifi = savedActor.getInverseFunctionalIdentifier();
        assertNotNull(ifi);
        assertEquals(mbox, ifi.getMbox());
        assertEquals(mbox_sha1sum, ifi.getMbox_sha1sum());
        assertEquals(openId, ifi.getOpenid());

        Account account = ifi.getAccount();
        assertNotNull(account);
        assertEquals(homepage, account.getHomePage());
        assertEquals(accountName, account.getName());
    }

    @Test
    public void shouldPersistVerb() {
        Statement savedStatement = entityManager.find(Statement.class, statementId);
        assertNotNull(savedStatement);

        Verb foundVerb = savedStatement.getVerb();
        assertNotNull(foundVerb);
        assertEquals(verbId, foundVerb.getId());
        assertEquals(displayName, foundVerb.getDisplay().get(dispalyLanguage));
    }

    @Test
    public void shouldPersistXapiObject() {
        Statement savedStatement = entityManager.find(Statement.class, statementId);
        assertNotNull(savedStatement);

        XapiObject xapiObject = savedStatement.getObject();
        assertNotNull(xapiObject);
        assertEquals(objectType, xapiObject.getObjectType());
    }
}
