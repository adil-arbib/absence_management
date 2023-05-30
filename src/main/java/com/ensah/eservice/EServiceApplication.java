package com.ensah.eservice;

import com.ensah.eservice.models.Compte;
import com.ensah.eservice.models.Role;
import com.ensah.eservice.models.SuperAdmin;
import com.ensah.eservice.repositories.CompteRepository;
import com.ensah.eservice.repositories.SuperAdminRepository;
import com.ensah.eservice.security.PasswordEncoder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EServiceApplication.class, args);
    }



}
