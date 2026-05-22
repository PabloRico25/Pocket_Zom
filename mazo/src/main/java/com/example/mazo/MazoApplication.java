package com.example.mazo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MazoApplication {
    public static void main(String[] args) {
        SpringApplication.run(MazoApplication.class, args);
    }
}