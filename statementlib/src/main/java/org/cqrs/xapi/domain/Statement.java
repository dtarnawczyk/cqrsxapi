package org.cqrs.xapi.domain;

import lombok.Data;
import org.cqrs.xapi.domain.util.Constants;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Immutable
public class Statement {

    @Id
    private String id;

    @NotNull
    @OneToOne(mappedBy = "statement",
            cascade = CascadeType.ALL)
    private Actor actor;

    @NotNull
    @OneToOne(mappedBy = "statement",
            cascade = CascadeType.ALL)
    private Verb verb;

    @NotNull
    @OneToOne(mappedBy = "statement",
            cascade = CascadeType.ALL)
    private XapiObject object;

    protected Statement(){}

    public Statement(String uuid){
        this.id = uuid;
    }
}
