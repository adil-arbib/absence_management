package com.ensah.eservice.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PieceJustificative {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String intitule;

    @Column(nullable = false)
    private Date dateLivraison;

    @OneToOne
    private File source;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "pieceJustificatives")
    private List<Absence> absences;
}
