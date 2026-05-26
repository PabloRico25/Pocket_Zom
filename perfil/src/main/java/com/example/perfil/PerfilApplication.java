package com.example.perfil;

import com.example.perfil.model.Rol;
import com.example.perfil.repository.RolRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class PerfilApplication {
    public static void main(String[] args) {
        SpringApplication.run(PerfilApplication.class, args);
    }

    @Bean
    public CommandLineRunner initRoles(RolRepository rolRepository) {
        return args -> {

            if (rolRepository.findByNombre("ROLE_PLAYER").isEmpty()) {
                Rol playerRol = new Rol();
                playerRol.setNombre("ROLE_PLAYER");
                rolRepository.save(playerRol);
                System.out.println("ROLE_PLAYER creado con ID: " + playerRol.getId() + ")");
            }

            if (rolRepository.findByNombre("ROLE_ADMIN").isEmpty()) {
                Rol adminRol = new Rol();
                adminRol.setNombre("ROLE_ADMIN");
                rolRepository.save(adminRol);
                System.out.println("ROLE_ADMIN creado con ID: " + adminRol.getId() + ")");
            }
        };
    }
}