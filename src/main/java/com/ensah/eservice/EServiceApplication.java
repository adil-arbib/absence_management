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


    @Bean
    CommandLineRunner commandLineRunner(
            SuperAdminRepository superAdminRepository,
            CompteRepository compteRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            SuperAdmin superAdmin = new SuperAdmin();
            superAdmin.setEmail("admin@gmail.com");
            superAdmin.setNom("admin");
            superAdmin.setPrenom("admin");
            superAdmin.setNomArabe("أدمين");
            superAdmin.setPrenomArab("أدمين");
            superAdmin.setRole(Role.SUPER_ADMIN);
            superAdmin.setTel("0689070809");

            Compte compte = new Compte();
            compte.setUsername("admin");
            compte.setPassword(passwordEncoder.bCryptPasswordEncoder().encode("123"));
            compte.setUtilisateur(superAdminRepository.save(superAdmin));
            compte.setEnabled(true);
            compte.setAccountNotExpired(true);
            compte.setAccountNotLocked(true);
            compte.setDisconnectAccount(false);
            compteRepository.save(compte);
        };
    }

}
