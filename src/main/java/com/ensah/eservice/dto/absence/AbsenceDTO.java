package com.ensah.eservice.dto.absence;

import com.ensah.eservice.dto.TypeSeance.TypeSeanceDTO;
import com.ensah.eservice.dto.elements.ElementDTO;
import com.ensah.eservice.dto.inscription.InscriptionDTO;
import com.ensah.eservice.dto.users.enseignant.EnseignantDTO;
import com.ensah.eservice.dto.users.etudiant.EtudiantDTO;
import com.ensah.eservice.models.AbsenceEtat;
import com.ensah.eservice.models.Enseignant;
import com.ensah.eservice.models.Etudiant;
import com.ensah.eservice.models.TypeSeance;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class AbsenceDTO {

    private Long id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date absenceStart;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date absenceEnd;
    private AbsenceEtat etat;
    private ElementDTO element;
    private TypeSeanceDTO typeSeance;
    private EnseignantDTO enseignant;
    private InscriptionDTO inscription;

}
