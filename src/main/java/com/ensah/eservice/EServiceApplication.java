package com.ensah.eservice;

import com.ensah.eservice.exceptions.notfound.NotFoundException;
import com.ensah.eservice.models.*;
import com.ensah.eservice.repositories.*;
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

//    @Bean
//    public CommandLineRunner runner(AccreditationRepository accreditationRepository, EnseignantRepository enseignantRepository) {
//        return args -> {
//
//            Accreditation accreditation = new Accreditation();
//
//
//
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
//
//
//        };
//
//
//    }

//
//    @Bean
//    CommandLineRunner commandLineRunner(
////            SuperAdminRepository superAdminRepository,
//            CompteRepository compteRepository,
//            PasswordEncoder passwordEncoder,
//            EtudiantRepository etudiantRepository
//
//    ) {
//        return args -> {
////            SuperAdmin superAdmin = new SuperAdmin();
////            superAdmin.setNom("admin");
////            superAdmin.setPrenom("admin");
////            superAdmin.setNomArabe("أدمين");
////            superAdmin.setPrenomArab("أدمين");
////            superAdmin.setEmail("admin@gmail.com");
////            superAdmin.setTel("0689070809");
////            superAdmin.setRole(Role.SUPER_ADMIN);
//
//            Compte compte = new Compte();
//            compte.setUsername("etudiant1");
//            compte.setPassword(passwordEncoder.bCryptPasswordEncoder().encode("123"));
//            compte.setUtilisateur(etudiantRepository.getReferenceById(9L));
//            compte.setEnabled(true);
//            compte.setAccountNotExpired(true);
//            compte.setAccountNotLocked(true);
//            compteRepository.save(compte);
//        };
//    }

//
//    @Bean
//    CommandLineRunner commandLineRunner(
//            InscriptionRepository inscriptionRepository,
//            NiveauRepository niveauRepository,
//            EtudiantRepository etudiantRepository
//    ) {
//        return args -> {
//            Etudiant etudiant = etudiantRepository.findById(13L).orElse(null);
//            Inscription inscription = new Inscription();
//            inscription.setAnnee(2023);
//
//            inscription.setNiveau(niveauRepository.getReferenceById(2L));
//            inscription.setEtudiant(etudiant);
//            inscriptionRepository.save(inscription);
//        };
//    }

//        @Bean
//    CommandLineRunner commandLineRunner(
//            InscriptionRepository inscriptionRepository,
//            EtudiantRepository etudiantRepository,
//            NiveauRepository niveauRepository
//    ) {
//        return args -> {
//
//            Inscription inscription = new Inscription();
//            inscription.setEtudiant(etudiantRepository.findById(3L).orElse(null));
//            inscription.setNiveau(niveauRepository.findById(4L).orElse(null));
//            inscription.setAnnee(2022);
//
//            inscriptionRepository.save(inscription);
//
//
//
//
//        };
//    }

}
