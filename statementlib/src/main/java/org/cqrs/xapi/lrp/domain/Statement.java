package org.cqrs.xapi.lrp.domain;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Data
@Entity
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

    @Override
    public String toString() {
        return "Statement{" +
                "id='" + id + '\'' +
                ", actor=" + actor +
                ", verb=" + verb +
                ", object=" + object +
                '}';
    }
}
