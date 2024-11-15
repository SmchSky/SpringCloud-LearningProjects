package com.smy.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
@EnableFeignClients
public class OrderApplication {
    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(OrderApplication.class, args);
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        int i = 1;
        while (i < 1000) {
            String userName = environment.getProperty("user.name");
            String userAge = environment.getProperty("user.age");
            String userSex = environment.getProperty("user.sex");
            System.err.println("User's name is " + userName + ", age is " + userAge + ", sex is " + userSex);
            TimeUnit.SECONDS.sleep(3);
            i++;
        }
    }
}
