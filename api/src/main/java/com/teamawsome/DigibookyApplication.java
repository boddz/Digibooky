package com.teamawsome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class DigibookyApplication {

    public static void main(String[] args) {

        SpringApplication.run(DigibookyApplication.class, args);
    }

}
