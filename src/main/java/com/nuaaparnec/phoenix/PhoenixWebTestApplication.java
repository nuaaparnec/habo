package com.nuaaparnec.phoenix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:beanRefContext.xml")
@EnableAutoConfiguration
@Configuration
public class PhoenixWebTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(PhoenixWebTestApplication.class, args);
    }
}
