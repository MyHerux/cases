package com.heroxu.cases.cas;

import net.unicon.cas.client.configuration.EnableCasClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableCasClient
@SpringBootApplication
public class CasApplication {

    public static void main(String[] args) {
        SpringApplication.run(CasApplication.class, args);
    }
}
