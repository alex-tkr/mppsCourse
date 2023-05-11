package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import java.util.Collections;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class SpringAppl {
    public static void main(String[] args) {
       // SpringApplication.run(SpringAppl.class, args);
       SpringApplication app = new SpringApplication(SpringAppl.class);
        //app.setDefaultProperties(Collections.singletonMap("server.port", "8083"));
        app.run(args);


    }
}