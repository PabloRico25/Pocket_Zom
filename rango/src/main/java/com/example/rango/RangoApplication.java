package com.example.rango;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class RangoApplication {
    public static void main(String[] args) {
        SpringApplication.run(RangoApplication.class, args);
    }
}