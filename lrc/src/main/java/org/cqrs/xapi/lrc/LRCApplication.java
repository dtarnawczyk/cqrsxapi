package org.cqrs.xapi.lrc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("org.cqrs.xapi")
public class LRCApplication {
    public static void main(String[] args) {
        SpringApplication.run(LRCApplication.class, args);
    }
}
