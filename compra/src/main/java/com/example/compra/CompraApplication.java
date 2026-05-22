package com.example.compra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CompraApplication {
    public static void main(String[] args) {
        SpringApplication.run(CompraApplication.class, args);
    }
}
