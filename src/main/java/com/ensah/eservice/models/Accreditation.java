package com.ensah.eservice.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Accreditation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date debutAccreditation;

    @Temporal(TemporalType.DATE)
    private Date finAccreditation;

    @OneToOne
    private Enseignant coordinateur;



}
