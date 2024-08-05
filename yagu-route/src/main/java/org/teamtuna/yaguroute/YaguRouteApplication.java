package org.teamtuna.yaguroute;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class YaguRouteApplication {

    public static void main(String[] args) {
        SpringApplication.run(YaguRouteApplication.class, args);
    }
}
