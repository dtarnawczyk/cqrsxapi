package org.cqrs.xapi.domain;

import lombok.Getter;
import lombok.Setter;
import org.cqrs.xapi.domain.util.Constants;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
public class XapiObject {

    @Id
    @Getter
    @GeneratedValue(generator = Constants.STATEMENT_ID)
    private String parentId;

    @OneToOne(optional = false)
    @PrimaryKeyJoinColumn
    private Statement statement;

    private XapiObject() {}

    public XapiObject(Statement statement){
        this.statement = statement;
    }

    @Getter
    @Setter
    private String objectType; // Activity, Agent/Group, SubStatement, StatementRef

    @Getter
    private String id;

    // TODO:
/*   private Activity definition;

    // when the objectType is a StatementRef
    private String referenceId;

    // when the objectType is an Agent/Group - attributes of an Actor
    private String name;
    private String mbox;
    private String mbox_sha1sum;
    private String openid;
    private Collection<Actor> member;
    private Account account;

    // when an Object is an Substatement - attributes of a Statement
    private Actor actor;
    private Verb verb;
    private XapiObject object;
    private Result result;
    private Context context;
    private LocalDateTime timestamp;
    private Collection<Attachment> attachments;
*/
}
