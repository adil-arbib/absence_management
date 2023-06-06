package com.ensah.eservice.models;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
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
    private Element element;

    @ManyToOne
    private TypeSeance typeSeance;

    @ManyToOne
    private Enseignant enseignant;

    @ManyToOne
    private Inscription inscription;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable( joinColumns = @JoinColumn(name="absence_id"),
            inverseJoinColumns = @JoinColumn(name="pieceJustificative_id"))
    private List<PieceJustificative> pieceJustificatives;




}
