package org.cqrs.xapi.eventprocessor.repository;

import org.cqrs.xapi.lrp.domain.Statement;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatementRepository extends CrudRepository<Statement, String> {
}
