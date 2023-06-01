package com.ensah.eservice;

import com.ensah.eservice.models.*;
import com.ensah.eservice.repositories.AccreditationRepository;
import com.ensah.eservice.repositories.CompteRepository;
import com.ensah.eservice.repositories.EnseignantRepository;
import com.ensah.eservice.repositories.SuperAdminRepository;
import com.ensah.eservice.security.PasswordEncoder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class EServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(AccreditationRepository accreditationRepository, EnseignantRepository enseignantRepository) {
        return args -> {

            Accreditation accreditation = new Accreditation();


//            Enseignant enseignant = new Enseignant("R115522");
//            enseignant.setNom("doe");
//            enseignant.setNomArabe("?");
//            enseignant.setNom("doe");
//            enseignant.setPrenom("jane");
//            enseignant.setPrenomArab("??");
//            enseignant.setEmail("a@test.com");
//            enseignant.setTel("000000000");
//            enseignant.setRole(Role.ENSEIGNANT);
//
//
//
//            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//
//            accreditation.setDebutAccreditation(formatter.parse("2017-04-12"));
//            accreditation.setFinAccreditation(formatter.parse("2022-04-12"));
//            accreditation.setCoordinateur(enseignant);
//
//            enseignantRepository.save(enseignant);
//            accreditationRepository.save(accreditation);
//
//


        };


    }
}
