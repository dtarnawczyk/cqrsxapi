package org.cqrs.xapi.eventprocessor.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cqrs.xapi.lrp.domain.Statement;
import org.cqrs.xapi.eventprocessor.repository.StatementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class EventStatementWriteService implements StatementWriteService {

    @Autowired
    private final StatementRepository repository;

    @Override
    public void saveStatement(Statement statement) {
        log.info("Saving statement:" + statement);
        repository.save(statement);
    }

    @Override
    public void updateStatement(Statement statement) {
        log.info("Updating statement:" + statement);
        repository.save(statement);
    }

    @Override
    public void deleteStatement(Statement statement) {
        log.info("Deleting statement:" + statement);
        repository.delete(statement);
    }
}
