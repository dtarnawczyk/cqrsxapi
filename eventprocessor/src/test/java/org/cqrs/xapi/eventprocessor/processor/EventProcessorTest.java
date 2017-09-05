package org.cqrs.xapi.eventprocessor.processor;

import org.cqrs.xapi.eventprocessor.repository.StatementRepository;
import org.cqrs.xapi.eventprocessor.repository.config.RepositoryConfiguration;
import org.cqrs.xapi.eventprocessor.service.EventStatementWriteService;
import org.cqrs.xapi.eventprocessor.service.config.ProcessorConfiguration;
import org.cqrs.xapi.eventprocessor.service.processor.EventProcessor;
import org.cqrs.xapi.eventprocessor.service.processor.ReceiveEventService;
import org.cqrs.xapi.lrp.domain.*;
import org.cqrs.xapi.lrp.domain.event.CreateStatementEvent;
import org.cqrs.xapi.lrp.domain.event.DeleteStatementEvent;
import org.cqrs.xapi.lrp.domain.event.UpdateStatementEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        RepositoryConfiguration.class,
        EventProcessor.class,
        EventStatementWriteService.class,
        StatementRepository.class,
        ProcessorConfiguration.class})
@DataJpaTest
@DirtiesContext
public class EventProcessorTest {

    @Autowired
    private ReceiveEventService receiveEventService;

    @Autowired
    private StatementRepository repository;

    @Test
    public void whenReceivingCreateEvent_thenStatementWriteServiceShouldSave () {

        // Given
        String testStatementId = UUID.randomUUID().toString();
        Statement testStatement = prepareStatement(testStatementId);

        // When
        CreateStatementEvent createStatementEvent = new CreateStatementEvent(testStatement);
        receiveEventService.receiveCreateEvent(createStatementEvent);

        // Then
        Statement foundStatement = repository.findOne(testStatementId);
        assertNotNull(foundStatement);
        assertEquals(testStatementId, foundStatement.getId());
    }

    @Test
    public void whenReceiveUpdateEvent_thenStatementWriteServiceShouldUpdate () {

        // Given
        String statementId = UUID.randomUUID().toString();
        repository.save(prepareStatement(statementId));

        // When
        Statement statement = repository.findOne(statementId);
        String updatedActorsName = "Updated test actor";
        statement.getActor().setName(updatedActorsName);
        UpdateStatementEvent updateStatementEvent = new UpdateStatementEvent(statement);
        receiveEventService.receiveUpdateEvent(updateStatementEvent);

        // Then
        Statement foundStatement = repository.findOne(statementId);
        assertEquals(updatedActorsName, statement.getActor().getName());
    }

    @Test
    public void whenReceiveDeleteEvent_thenStatementWriteServiceShouldDelete () {

        // Given
        String testStatementId = UUID.randomUUID().toString();
        repository.save(prepareStatement(testStatementId));

        // When
        Statement statement = repository.findOne(testStatementId);
        DeleteStatementEvent deleteStatementEvent = new DeleteStatementEvent(statement);
        receiveEventService.receiveDeleteEvent(deleteStatementEvent);

        // Then
        assertNull(repository.findOne(testStatementId));
    }

    private Statement prepareStatement(String statementId){
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

        IFI inverseFunctionalIdentifier = new IFI();

        inverseFunctionalIdentifier.setMbox(mbox);
        inverseFunctionalIdentifier.setMbox_sha1sum(mbox_sha1sum);
        inverseFunctionalIdentifier.setOpenid(openId);

        Account testAccount = new Account(homepage, accountName);
        inverseFunctionalIdentifier.setAccount(testAccount);

        Statement testStatement = new Statement(statementId);

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

        return testStatement;
    }
}