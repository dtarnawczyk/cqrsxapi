package org.cqrs.xapi.lrp.domain;

import lombok.Getter;
import lombok.Setter;
import org.cqrs.xapi.lrp.domain.util.Constants;

import javax.persistence.*;
import java.util.Map;

@Entity
public class Verb {

    @Id
    @Getter
    @GeneratedValue(generator = Constants.STATEMENT_ID)
    private String verb_id;

    @OneToOne(optional = false)
    @PrimaryKeyJoinColumn
    private Statement statement;

    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    @ElementCollection
    @CollectionTable(name = "display")
    @MapKeyColumn(name = "language")
    @Column(name = "displayed")
    private Map<String, String> display;

    private Verb() {}

    public Verb(Statement statement){
        this.statement = statement;
    }
}
