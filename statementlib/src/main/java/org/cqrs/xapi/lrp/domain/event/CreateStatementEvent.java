package org.cqrs.xapi.lrp.domain.event;

import lombok.Getter;
import lombok.Value;
import org.cqrs.xapi.lrp.domain.Statement;

import java.time.LocalDateTime;
import java.util.UUID;

@Value
@Getter
public class CreateStatementEvent {

    private final String id;
    private final LocalDateTime createdAt;
    private final Statement statement;

    @SuppressWarnings("unused")
    private CreateStatementEvent() {
        this.id = null;
        this.createdAt = null;
        this.statement = null;
    }

    public CreateStatementEvent(Statement statement){
        this.id = UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
        this.statement = statement;
    }
}
