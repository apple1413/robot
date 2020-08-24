package com.zty.robot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@ComponentScans({@ComponentScan("com.zty.robot"), @ComponentScan("com.zty.robot.KnowBasePort")})

public class RobotTestApplication {

    public static void main(String[] args) {

        SpringApplication.run(RobotTestApplication.class, args);
    }
}
