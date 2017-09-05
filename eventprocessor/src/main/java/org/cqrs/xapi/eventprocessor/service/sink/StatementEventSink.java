package org.cqrs.xapi.eventprocessor.service.sink;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface StatementEventSink {

    public static final String CREATE = "statementCreateSink";
    public static final String UPDATE = "statementUpdateSink";
    public static final String DELETE = "statementDeleteSink";

    @Input(StatementEventSink.CREATE)
    SubscribableChannel receiveStatementCreate();

    @Input(StatementEventSink.UPDATE)
    SubscribableChannel receiveStatementUpdate();

    @Input(StatementEventSink.DELETE)
    SubscribableChannel receiveStatementDelete();
}
