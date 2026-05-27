package com.example.perfil;

import com.example.perfil.model.Rol;
import com.example.perfil.repository.RolRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
@Slf4j
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
                Rol rol = new Rol();
                rol.setNombre("ROLE_PLAYER");
                rolRepository.save(rol);
                log.info("ROLE_PLAYER creado");
            }
            if (rolRepository.findByNombre("ROLE_ADMIN").isEmpty()) {
                Rol rol = new Rol();
                rol.setNombre("ROLE_ADMIN");
                rolRepository.save(rol);
                log.info("ROLE_ADMIN creado");
            }
        };
    }
}