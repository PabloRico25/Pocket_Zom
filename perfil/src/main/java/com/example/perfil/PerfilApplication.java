package com.example.perfil;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PerfilApplication {
    public static void main(String[] args) {
        SpringApplication.run(PerfilApplication.class, args);
    }
}