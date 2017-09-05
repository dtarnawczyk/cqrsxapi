package org.cqrs.xapi.lrp.service.config;

import org.cqrs.xapi.lrp.service.source.StatementEventSource;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@EnableBinding(StatementEventSource.class)
public class EventServiceConfiguration {
}
