package com.ensah.eservice.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class JournalisationEvenements {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String details;

    @Column(nullable = false)
    private String adresseIP;

    @Column(nullable = false)
    private Date DateHeure;

    @Column(nullable = false)
    private String typeEvenement;

    @Column(nullable = false)
    private String criticite;

    @ManyToOne
    private Utilisateur utilisateur;

}
