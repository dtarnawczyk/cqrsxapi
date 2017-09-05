package org.cqrs.xapi.lrp;

import org.cqrs.xapi.eventprocessor.service.config.ProcessorConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(ProcessorConfiguration.class)
public class LRCApplication {
    public static void main(String[] args) {
        SpringApplication.run(LRCApplication.class, args);
    }
}
