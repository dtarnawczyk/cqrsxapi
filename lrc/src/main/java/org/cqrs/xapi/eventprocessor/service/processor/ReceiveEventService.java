package org.cqrs.xapi.eventprocessor.service.processor;

import org.cqrs.xapi.lrp.domain.event.CreateStatementEvent;
import org.cqrs.xapi.lrp.domain.event.DeleteStatementEvent;
import org.cqrs.xapi.lrp.domain.event.UpdateStatementEvent;

public interface ReceiveEventService {
    void receiveCreateEvent(CreateStatementEvent event);
    void receiveUpdateActorEvent(UpdateStatementEvent event);
    void receiveUpdateVerbEvent(UpdateStatementEvent event);
    void receiveUpdateObjectEvent(UpdateStatementEvent event);
    void receiveDeleteEvent(DeleteStatementEvent event);
}
