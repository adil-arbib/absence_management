package com.ensah.eservice.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
public class Utilisateur
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(nullable = false)
    protected String nom;

    @Column(nullable = false)
    protected String prenom;

    @Column(nullable = false)
    protected String email;

    protected String tel;

    @Column(nullable = false)
    protected String nomArabe;

    @Column(nullable = false)
    protected String prenomArab;

    @OneToOne
    protected File image;

    @Enumerated(EnumType.STRING)
    protected Role role;


}
