package org.cqrs.xapi.eventprocessor.service.processor;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cqrs.xapi.eventprocessor.service.StatementWriteService;
import org.cqrs.xapi.eventprocessor.service.sink.StatementEventSink;
import org.cqrs.xapi.lrp.domain.event.CreateStatementEvent;
import org.cqrs.xapi.lrp.domain.event.DeleteStatementEvent;
import org.cqrs.xapi.lrp.domain.event.UpdateStatementEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.context.annotation.Configuration;

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
        statementWriteService.saveStatement(event.getStatement());
    }

    @Override
    @StreamListener(StatementEventSink.UPDATE)
    public void receiveUpdateEvent(UpdateStatementEvent event) {
        log.info("Received update event id: "+ event.getId() + ", created at: "+ event.getCreatedAt());
        statementWriteService.updateStatement(event.getStatement());
    }

    @Override
    @StreamListener(StatementEventSink.DELETE)
    public void receiveDeleteEvent(DeleteStatementEvent event) {
        log.info("Received delete event id: "+ event.getId()+ ", created at: "+ event.getCreatedAt());
        statementWriteService.deleteStatement(event.getStatement());
    }
}
