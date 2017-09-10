package org.cqrs.xapi.lrp.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.cqrs.xapi.lrp.LRPApplication;
import org.cqrs.xapi.lrp.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LRPApplication.class)
@AutoConfigureMockMvc
public class CommandControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void initMockMvc() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void givenEventService_whenPostWithStatement_thenReturnCreateStatementEvent() throws Exception {
        String testStatementId = UUID.randomUUID().toString();
        Statement testStatement = prepareStatement(testStatementId);
        mockMvc.perform(
                post("/statement")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(testStatement)))
        .andExpect(status().isCreated())
        .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("statement.id").value(testStatementId))
        .andDo(print());
    }

    @Test
    public void givenEventService_whenUpdateWithStatement_thenReturnUpdateStatementEvent() throws Exception {
        String testStatementId = UUID.randomUUID().toString();
        Statement testStatement = prepareStatement(testStatementId);
        mockMvc.perform(
                put("/statement")
                        .header("type","actor")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(testStatement)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("statement.id").value(testStatementId))
                .andDo(print());
    }

    @Test
    public void givenEventService_whenUpdateWithStatement_thenReturnDeleteStatementEvent() throws Exception {
        String testStatementId = UUID.randomUUID().toString();
        Statement testStatement = prepareStatement(testStatementId);
        mockMvc.perform(
                delete("/statement")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(testStatement)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("statement.id").value(testStatementId))
                .andDo(print());
    }


    private Statement prepareStatement(String statementId){
        // ACTOR
        String actorsName = "Test Actor";
        String actorsObjectType = "Agent";

        // IFI
        String mbox = "mailto:test@mail.com";
        String mbox_sha1sum = "444444444";
        String openId = "1234567";

        // ACCOUNT
        String homepage = "www.google.pl";
        String accountName = "test";

        // VERB
        String verbId = "Human readable verb";
        String displayName = "testowanie";
        String dispalyLanguage = "pl_PL";

        // OBJECT
        String objectType = "Activity";

        IFI inverseFunctionalIdentifier = new IFI();

        inverseFunctionalIdentifier.setMbox(mbox);
        inverseFunctionalIdentifier.setMbox_sha1sum(mbox_sha1sum);
        inverseFunctionalIdentifier.setOpenid(openId);

        Account testAccount = new Account(homepage, accountName);
        inverseFunctionalIdentifier.setAccount(testAccount);

        Statement testStatement = new Statement(statementId);

        Actor testActor = new Actor(testStatement);
        testActor.setName(actorsName);
        testActor.setObjectType(actorsObjectType);

        testActor.setInverseFunctionalIdentifier(inverseFunctionalIdentifier);

        testStatement.setActor(testActor);

        Verb verb = new Verb(testStatement);
        verb.setId(verbId);
        Map<String, String> displayMap = new HashMap<>();
        displayMap.put(dispalyLanguage, displayName);
        verb.setDisplay(displayMap);

        testStatement.setVerb(verb);

        XapiObject xapiObject = new XapiObject(testStatement);
        xapiObject.setObjectType(objectType);

        testStatement.setObject(xapiObject);

        return testStatement;
    }

}
