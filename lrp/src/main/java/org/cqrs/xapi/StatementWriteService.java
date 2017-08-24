package org.cqrs.xapi;

import org.cqrs.xapi.domain.Statement;

public interface StatementWriteService {
    public void saveStatement(Statement statement);
    public void updateStatement(Statement statement);
    public void deletetatement(Statement statement);
}
