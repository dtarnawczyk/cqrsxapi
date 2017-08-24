package org.cqrs.xapi.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Statement {

    @Id
    private String id;

    private String name;

    protected Statement(){}

    public Statement(String uuid){
        this.id = uuid;
    }
}
