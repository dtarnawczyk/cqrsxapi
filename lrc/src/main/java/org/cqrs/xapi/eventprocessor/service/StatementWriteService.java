package org.cqrs.xapi.eventprocessor.service;

import org.cqrs.xapi.lrp.domain.Statement;

public interface StatementWriteService {
    public void saveStatement(Statement statement);
    public void updateStatement(Statement statement);
    public void deleteStatement(Statement statement);
}
