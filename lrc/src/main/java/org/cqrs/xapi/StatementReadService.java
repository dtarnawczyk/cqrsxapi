package org.cqrs.xapi;

import org.cqrs.xapi.domain.Statement;

import java.util.List;

public interface StatementReadService {
    public Statement getStatement(Long statementId);
    public List<Statement> findStatements();
}
