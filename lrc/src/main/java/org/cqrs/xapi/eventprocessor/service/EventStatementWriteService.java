package org.cqrs.xapi.eventprocessor.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cqrs.xapi.eventprocessor.repository.StatementRepository;
import org.cqrs.xapi.lrp.domain.Actor;
import org.cqrs.xapi.lrp.domain.Statement;
import org.cqrs.xapi.lrp.domain.Verb;
import org.cqrs.xapi.lrp.domain.XapiObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
public class EventStatementWriteService implements StatementWriteService {

    @Autowired
    private final StatementRepository repository;

    @Override
    @Transactional
    public void saveStatement(Statement statement) {
        log.info("Saving Statement:" + statement);
        repository.save(statement);
    }

    @Override
    @Transactional
    public void updateStatementActor(Statement statementDto) {
        log.info("Updating Statement Actor:" + statementDto);
        Actor actorDto = statementDto.getActor();
        Statement statement = repository.findOne(statementDto.getId());
        statement.getActor().setName(actorDto.getName());
        statement.getActor().setObjectType(actorDto.getName());
        statement.getActor().setInverseFunctionalIdentifier(actorDto.getInverseFunctionalIdentifier());
    }

    @Override
    @Transactional
    public void updateStatementVerb(Statement statementDto) {
        log.info("Updating Statement Verb:" + statementDto);
        Verb verbDto = statementDto.getVerb();
        Statement statement = repository.findOne(statementDto.getId());
        statement.getVerb().setDisplay(verbDto.getDisplay());
    }

    @Override
    @Transactional
    public void updateStatementXapiObject(Statement statementDto) {
        log.info("Updating Statement XapiObject:" + statementDto);
        XapiObject object = statementDto.getObject();
        Statement statement = repository.findOne(statementDto.getId());
        statement.getObject().setObjectType(object.getObjectType());
    }

    @Override
    public void deleteStatement(Statement statement) {
        log.info("Deleting statement:" + statement);
        repository.delete(statement);
    }
}
