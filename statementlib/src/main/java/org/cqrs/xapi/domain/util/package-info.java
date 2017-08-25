@GenericGenerators({
        @GenericGenerator(
                name = Constants.ID_GENERATOR,
                strategy = "enhanced-sequence",
                parameters = {
                        @Parameter(
                                name = "sequence_name",
                                value = Constants.ID_GENERATOR_SEQUENCE_NAME
                        ),
                        @Parameter(
                                name = "initial_value",
                                value = "1000"
                        ),
                        @Parameter(
                                name = "increment_size",
                                value = "1"
                        ),
                        @Parameter(
                                name = "optimizer",
                                value = "pooled-lo"
                        )
                }),
        @GenericGenerator(name = Constants.STATEMENT_ID,
                strategy = "foreign",
                parameters =
                @Parameter(name = "property", value = "statement")
        )
})
@NamedQueries(value = {
        @NamedQuery(name = "getStatements", query = "SELECT s FROM Statement s")
})
package org.cqrs.xapi.domain.util;
import org.hibernate.annotations.*;
