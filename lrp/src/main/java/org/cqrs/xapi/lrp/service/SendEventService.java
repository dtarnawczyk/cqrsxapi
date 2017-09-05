package org.cqrs.xapi.lrp.service;

import org.cqrs.xapi.lrp.domain.event.CreateStatementEvent;
import org.cqrs.xapi.lrp.domain.event.DeleteStatementEvent;
import org.cqrs.xapi.lrp.domain.event.UpdateStatementEvent;

public interface SendEventService {
    void sendCreateEvent(CreateStatementEvent event);
    void sendUpdateEvent(UpdateStatementEvent event);
    void sendDeleteEvent(DeleteStatementEvent event);
}
