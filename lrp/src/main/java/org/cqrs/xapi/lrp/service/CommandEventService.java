package org.cqrs.xapi.lrp.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cqrs.xapi.lrp.domain.event.CreateStatementEvent;
import org.cqrs.xapi.lrp.domain.event.UpdateStatementEvent;
import org.cqrs.xapi.lrp.service.source.StatementEventSource;
import org.cqrs.xapi.lrp.domain.event.DeleteStatementEvent;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class CommandEventService implements SendEventService {

    private final StatementEventSource source;

    @Override
    public void sendCreateEvent(CreateStatementEvent event) {
        source.createStatement().send(MessageBuilder.withPayload(event).build());
    }

    @Override
    public void sendUpdateEvent(UpdateStatementEvent event, String updateType) {
        source.updateStatement().send(MessageBuilder.withPayload(event).setHeader("type", updateType).build());
    }

    @Override
    public void sendDeleteEvent(DeleteStatementEvent event) {
        source.deleteStatement().send(MessageBuilder.withPayload(event).build());
    }
}
