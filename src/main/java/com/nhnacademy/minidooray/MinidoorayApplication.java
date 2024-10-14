package com.nhnacademy.minidooray;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MinidoorayApplication {

    public static void main(String[] args) {
        SpringApplication.run(MinidoorayApplication.class, args);
    }

}
