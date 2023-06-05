package com.ensah.eservice.dto.inscription;

import com.ensah.eservice.dto.niveau.NiveauDTO;
import com.ensah.eservice.dto.users.etudiant.EtudiantDTO;
import com.ensah.eservice.models.Etudiant;
import com.ensah.eservice.models.Niveau;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class InscriptionDTO {
    private Long id;

    private Integer annee;

    private Integer etat;

    private EtudiantDTO etudiant;

    private NiveauDTO niveau;

}
