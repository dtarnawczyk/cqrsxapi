package org.cqrs.xapi.eventprocessor.service.config;

import org.cqrs.xapi.eventprocessor.service.sink.StatementEventSink;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBinding(StatementEventSink.class)
public class ProcessorConfiguration {
}
