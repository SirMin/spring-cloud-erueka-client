package com.min.spring.cloud.eureka.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Create By minzhiwei On 2018/12/21 14:39
 * TODO 功能描述
 */
@SpringBootApplication
@EnableEurekaClient
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
