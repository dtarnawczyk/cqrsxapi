package org.cqrs.xapi.eventprocessor.service;

import org.cqrs.xapi.lrp.domain.Statement;

public interface StatementWriteService {
    public void saveStatement(Statement statement);
    public void updateStatementActor(Statement statementDto);
    public void updateStatementVerb(Statement statementDto);
    public void updateStatementXapiObject(Statement statementDto);
    public void deleteStatement(Statement statement);
}
