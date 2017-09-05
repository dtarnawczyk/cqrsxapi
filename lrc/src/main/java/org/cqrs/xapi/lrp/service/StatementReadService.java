package org.cqrs.xapi.lrp.service;

import org.cqrs.xapi.lrp.domain.Statement;

import java.util.List;

public interface StatementReadService {
    public Statement getStatement(Long statementId);
    public List<Statement> findStatements();
}
