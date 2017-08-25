package org.cqrs.xapi.domain;

import lombok.Getter;
import lombok.Setter;
import org.cqrs.xapi.domain.util.Constants;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

@Entity
public class Actor {

    @Id
    @Getter
    @GeneratedValue(generator = Constants.STATEMENT_ID)
    private String id;

    @OneToOne(optional = false)
    @PrimaryKeyJoinColumn
    private Statement statement;

    private Actor() {}

    public Actor(Statement statement){
        this.statement = statement;
    }

    @Getter
    @Setter
    private String objectType; // Agent or Group

    @Getter
    @Setter
    private String name;

    // TODO:
//    private Collection<Actor> member;

    @Getter
    @Setter
    private IFI inverseFunctionalIdentifier;
}
