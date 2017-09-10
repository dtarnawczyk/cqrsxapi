package org.cqrs.xapi.eventprocessor.service.processor;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cqrs.xapi.eventprocessor.service.StatementWriteService;
import org.cqrs.xapi.eventprocessor.service.sink.StatementEventSink;
import org.cqrs.xapi.lrp.domain.Actor;
import org.cqrs.xapi.lrp.domain.Statement;
import org.cqrs.xapi.lrp.domain.Verb;
import org.cqrs.xapi.lrp.domain.XapiObject;
import org.cqrs.xapi.lrp.domain.event.CreateStatementEvent;
import org.cqrs.xapi.lrp.domain.event.DeleteStatementEvent;
import org.cqrs.xapi.lrp.domain.event.UpdateStatementEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.UUID;

@Configuration
@Slf4j
@AllArgsConstructor
public class EventProcessor implements ReceiveEventService {

    @Autowired
    private final StatementWriteService statementWriteService;

    @Override
    @StreamListener(StatementEventSink.CREATE)
    public void receiveCreateEvent(CreateStatementEvent event) {
        log.info("Received create event id: "+ event.getId() + ", created at: "+ event.getCreatedAt());
        Statement statement = prepareStatement(event.getStatement());
        statementWriteService.saveStatement(statement);
    }

    @Override
    @StreamListener(target = StatementEventSink.UPDATE)
    public void receiveUpdateEvent(Message<UpdateStatementEvent> eventMessage) {
        String updateType = (String) eventMessage.getHeaders().get("type");
        UpdateStatementEvent event = eventMessage.getPayload();
        log.info("Received update actor event id: "+ event.getId() + ", created at: "+ event.getCreatedAt());
        Statement statement = event.getStatement();
        switch(updateType){
            case "actor":
                statementWriteService.updateStatementActor(statement);
                break;
            case "object":
                statementWriteService.updateStatementXapiObject(statement);
                break;
            case "verb":
                statementWriteService.updateStatementVerb(statement);
                break;
            default:
                statementWriteService.updateStatementActor(statement);
        }
    }

    @Override
    @StreamListener(StatementEventSink.DELETE)
    public void receiveDeleteEvent(DeleteStatementEvent event) {
        log.info("Received delete event id: "+ event.getId()+ ", created at: "+ event.getCreatedAt());
        Statement statement = prepareStatement(event.getStatement());
        statementWriteService.deleteStatement(statement);
    }

    private Statement prepareStatement(final Statement statement){
        if(statement.getId() == null){
            statement.setId(UUID.randomUUID().toString());
        }

        Actor actorDto = statement.getActor();
        Actor actor = new Actor(statement);
        actor.setName(actorDto.getName());
        actor.setObjectType(actorDto.getObjectType());
        actor.setInverseFunctionalIdentifier(actorDto.getInverseFunctionalIdentifier());
        statement.setActor(actor);

        XapiObject objectDto = statement.getObject();
        XapiObject xapiObject = new XapiObject(statement);
        xapiObject.setObjectType(objectDto.getObjectType());
        statement.setObject(xapiObject);

        Verb verbDto = statement.getVerb();
        Verb verb = new Verb(statement);
        verb.setDisplay(verbDto.getDisplay());
        statement.setVerb(verb);

        return statement;
    }
}
