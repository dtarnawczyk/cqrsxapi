package org.cqrs.xapi.eventprocessor.service.processor;

import org.cqrs.xapi.lrp.domain.event.CreateStatementEvent;
import org.cqrs.xapi.lrp.domain.event.DeleteStatementEvent;
import org.cqrs.xapi.lrp.domain.event.UpdateStatementEvent;
import org.springframework.messaging.Message;

public interface ReceiveEventService {
    void receiveCreateEvent(CreateStatementEvent event);
    void receiveUpdateEvent(Message<UpdateStatementEvent> eventMessage);
    void receiveDeleteEvent(DeleteStatementEvent event);
}
