package org.cqrs.xapi.eventprocessor;

import org.cqrs.xapi.eventprocessor.repository.StatementRepository;
import org.cqrs.xapi.eventprocessor.service.processor.ReceiveEventService;
import org.cqrs.xapi.lrc.LRCApplication;
import org.cqrs.xapi.lrp.domain.*;
import org.cqrs.xapi.lrp.domain.event.CreateStatementEvent;
import org.cqrs.xapi.lrp.domain.event.DeleteStatementEvent;
import org.cqrs.xapi.lrp.domain.event.UpdateStatementEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LRCApplication.class)
@DataJpaTest
public class EventProcessorTest {

    @Autowired
    private ReceiveEventService receiveEventService;

    @Autowired
    private StatementRepository repository;

    private String testStatementId;
    private Statement testStatement;

    @Before
    public void saveTestStatement() {
        testStatementId = UUID.randomUUID().toString();
        testStatement = prepareStatement(testStatementId);
        repository.save(testStatement);
    }

    @After
    public void removeTestStatement() {
        if(repository.findOne(testStatementId) != null)
            repository.delete(testStatementId);
    }

    @Test
    public void whenReceivingCreateEvent_thenStatementWriteServiceShouldSave () {

        // Given
        String statementId = UUID.randomUUID().toString();
        Statement testStatement = prepareStatement(statementId);

        // When
        CreateStatementEvent createStatementEvent = new CreateStatementEvent(testStatement);
        receiveEventService.receiveCreateEvent(createStatementEvent);

        // Then
        Statement foundStatement = repository.findOne(statementId);
        assertNotNull(foundStatement);
        assertEquals(statementId, foundStatement.getId());
    }

    @Test
    public void whenReceiveUpdateActorEvent_thenStatementWriteServiceShouldUpdateActor () {
        // When
        String updatedActorsName = "Updated test actor";
        testStatement.getActor().setName(updatedActorsName);

        UpdateStatementEvent updateStatementEvent = new UpdateStatementEvent(testStatement);
        Message<UpdateStatementEvent> message = MessageBuilder
                .withPayload(updateStatementEvent)
                .setHeader("type", "actor").build();
        receiveEventService.receiveUpdateEvent(message);

        // Then
        Statement foundStatement = repository.findOne(testStatementId);
        assertEquals(updatedActorsName, foundStatement.getActor().getName());
    }

    @Test
    public void whenReceiveUpdateVerbEvent_thenStatementWriteServiceShouldUpdateVerb () {
        // When
        Map<String, String> testDisplay = Collections.unmodifiableMap(Stream.of(
                new AbstractMap.SimpleEntry<>("pl_PL", "wpis"),
                new AbstractMap.SimpleEntry<>("en_EN", "entry"))
                .collect(Collectors.toMap((e) -> e.getKey(), (e) -> e.getValue())));
        testStatement.getVerb().setDisplay(testDisplay);

        UpdateStatementEvent updateStatementEvent = new UpdateStatementEvent(testStatement);
        Message<UpdateStatementEvent> message = MessageBuilder
                .withPayload(updateStatementEvent)
                .setHeader("type", "verb").build();
        receiveEventService.receiveUpdateEvent(message);

        // Then
        Statement foundStatement = repository.findOne(testStatementId);
        Map<String, String> currentMap = foundStatement.getVerb().getDisplay();
        assertNotNull(currentMap);
        assertEquals(testDisplay.size(), currentMap.size());
        for(Map.Entry<String,String> value : testDisplay.entrySet()){
            String actualValue = currentMap.get(value.getKey());
            assertNotNull(actualValue);
            assertEquals(value.getValue(), actualValue);
        }
    }

    @Test
    public void whenReceiveUpdateObjectEvent_thenStatementWriteServiceShouldUpdateObject () {
        // When
        String updatedObjectType = "crossword";
        testStatement.getObject().setObjectType(updatedObjectType);

        UpdateStatementEvent updateStatementEvent = new UpdateStatementEvent(testStatement);
        Message<UpdateStatementEvent> message = MessageBuilder
                .withPayload(updateStatementEvent)
                .setHeader("type", "object").build();
        receiveEventService.receiveUpdateEvent(message);

        // Then
        Statement foundStatement = repository.findOne(testStatementId);
        assertEquals(updatedObjectType, foundStatement.getObject().getObjectType());
    }

    @Test
    public void whenReceiveDeleteEvent_thenStatementWriteServiceShouldDelete () {

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