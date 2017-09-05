package org.cqrs.xapi.lrp.domain;

import lombok.Data;

import javax.persistence.Embeddable;

@Embeddable
@Data
/*
 *   Inverse Functional Identifier
 */
public class IFI {
    private String mbox;
    private String mbox_sha1sum;
    private String openid;
    private Account account;
}
