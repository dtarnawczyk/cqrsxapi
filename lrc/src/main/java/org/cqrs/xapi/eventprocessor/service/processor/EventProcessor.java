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
    @StreamListener(target = StatementEventSink.UPDATE, condition = "headers['type']=='actor'")
    public void receiveUpdateActorEvent(UpdateStatementEvent event) {
        log.info("Received update actor event id: "+ event.getId() + ", created at: "+ event.getCreatedAt());
        Statement statement = prepareStatement(event.getStatement());
        statementWriteService.updateStatementActor(statement);
    }

    @Override
    @StreamListener(target = StatementEventSink.UPDATE, condition = "headers['type']=='verb'")
    public void receiveUpdateVerbEvent(UpdateStatementEvent event) {
        log.info("Received update verb event id: "+ event.getId() + ", created at: "+ event.getCreatedAt());
        Statement statement = prepareStatement(event.getStatement());
        statementWriteService.updateStatementVerb(statement);
    }

    @Override
    @StreamListener(target = StatementEventSink.UPDATE, condition = "headers['type']=='object'")
    public void receiveUpdateObjectEvent(UpdateStatementEvent event) {
        log.info("Received update object event id: "+ event.getId() + ", created at: "+ event.getCreatedAt());
        Statement statement = prepareStatement(event.getStatement());
        statementWriteService.updateStatementXapiObject(statement);
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
