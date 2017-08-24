package org.cqrs.xapi.domain.event;

import lombok.Value;
import org.cqrs.xapi.domain.Statement;

import java.time.LocalDateTime;
import java.util.UUID;

@Value
public class CreateStatementEvent {

    private String id;
    private LocalDateTime createdAt;
    private Statement statement;

    CreateStatementEvent(Statement statement){
        this.id = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
        this.statement = statement;
    }
}
