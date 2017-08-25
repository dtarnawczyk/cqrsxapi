package org.cqrs.xapi.domain;

import lombok.Value;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Value
@Embeddable
public class Account {

    @Column(name = "account_homePage")
    private String homePage;

    @Column(name = "account_name")
    private String name;

    @SuppressWarnings("unused")
    private Account(){
        this.homePage = null;
        this.name = null;
    }

    public Account(String homePage, String name){
        this.homePage = homePage;
        this.name = name;
    }
}