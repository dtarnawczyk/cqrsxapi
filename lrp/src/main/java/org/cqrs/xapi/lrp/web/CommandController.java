package org.cqrs.xapi.lrp.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cqrs.xapi.lrp.domain.event.UpdateStatementEvent;
import org.cqrs.xapi.lrp.domain.Statement;
import org.cqrs.xapi.lrp.domain.event.CreateStatementEvent;
import org.cqrs.xapi.lrp.domain.event.DeleteStatementEvent;
import org.cqrs.xapi.lrp.service.SendEventService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Slf4j
@AllArgsConstructor
public class CommandController {

    private final SendEventService sendService;

    @PostMapping("/statement")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateStatementEvent saveStatement(@RequestBody Statement statement){

        log.info(statement.toString());

        if(statement.getId() == null){
            statement.setId(UUID.randomUUID().toString());
        }
        CreateStatementEvent event = new CreateStatementEvent(statement);
        sendService.sendCreateEvent(event);

        return event;
    }

    @PutMapping("/statement")
    public UpdateStatementEvent updateStatement(@RequestBody Statement statement){

        log.info(statement.toString());

        UpdateStatementEvent event = new UpdateStatementEvent(statement);
        sendService.sendUpdateEvent(event);

        return event;
    }

    @DeleteMapping("/statement")
    public DeleteStatementEvent deleteStatement(@RequestBody Statement statement){

        log.info(statement.toString());

        if(statement.getId() == null){
            statement.setId(UUID.randomUUID().toString());
        }
        DeleteStatementEvent event = new DeleteStatementEvent(statement);
        sendService.sendDeleteEvent(event);

        return event;
    }

}
