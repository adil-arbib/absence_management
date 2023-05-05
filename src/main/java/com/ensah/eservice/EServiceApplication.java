package com.ensah.eservice;

import com.ensah.eservice.models.Compte;
import com.ensah.eservice.models.Role;
import com.ensah.eservice.models.SuperAdmin;
import com.ensah.eservice.repositories.CompteRepository;
import com.ensah.eservice.repositories.SuperAdminRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class EServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(SuperAdminRepository repository, CompteRepository compteRepository, PasswordEncoder encoder) {
        return args -> {
            SuperAdmin superAdmin = new SuperAdmin();
            superAdmin.setNom("admin");
            superAdmin.setPrenom("admin");
            superAdmin.setNomArabe("أدمين");
            superAdmin.setPrenomArab("أدمين");
            superAdmin.setEmail("adil2001@gmail.com");
            superAdmin.setTel("0689070809");
            superAdmin.setRole(Role.SUPER_ADMIN);
            SuperAdmin savedSa = repository.save(superAdmin);
            Compte compte = new Compte();
            compte.setEnabled(true);
            compte.setAccountNotExpired(true);
            compte.setAccountNotLocked(true);
            compte.setDisconnectAccount(true);
            compte.setUtilisateur(savedSa);
            compte.setUsername("admin");
            compte.setPassword(encoder.encode("1234"));
            compteRepository.save(compte);
        };
    }

}
