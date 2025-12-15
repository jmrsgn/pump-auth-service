package com.johnmartin.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class PumpAuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PumpAuthServiceApplication.class, args);
    }

}
