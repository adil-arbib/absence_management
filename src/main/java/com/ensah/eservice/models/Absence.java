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
public class Absence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Date absenceStart;

    @Column(nullable = false)

    private Date absenceEnd;
    @Column(nullable = false)

    @Enumerated(EnumType.STRING)
    private AbsenceEtat etat;

    @ManyToOne
    private TypeSeance typeSeance;

    @ManyToOne
    private Enseignant enseignant;

    @ManyToOne
    private Etudiant etudiant;


}
