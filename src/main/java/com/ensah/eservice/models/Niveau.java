package com.ensah.eservice.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Niveau {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String alias;

    private String titre;

    @OneToMany(mappedBy = "niveau" , cascade = CascadeType.ALL)
    private List<Inscription> inscriptions;

    @OneToMany
    private Collection<Module> modules;


}
