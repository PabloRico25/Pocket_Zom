package com.example.partida;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class PartidaApplication {

    public static void main(String[] args) {
        SpringApplication.run(PartidaApplication.class, args);
    }

}
