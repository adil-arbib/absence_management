package com.ensah.eservice.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

import java.sql.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Etudiant extends Utilisateur{

    @Column(nullable = false)
    private String cne;

    @Column(nullable = false)
    private Date dateNaissance;

    private boolean deleted;

}
