package org.cqrs.xapi.lrp.service.source;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface StatementEventSource {

    public static final String CREATE = "createStatement";
    public static final String UPDATE = "updateStatement";
    public static final String DELETE = "deleteStatement";

    @Output(StatementEventSource.CREATE)
    MessageChannel createStatement();

    @Output(StatementEventSource.UPDATE)
    MessageChannel updateStatement();

    @Output(StatementEventSource.DELETE)
    MessageChannel deleteStatement();

}
