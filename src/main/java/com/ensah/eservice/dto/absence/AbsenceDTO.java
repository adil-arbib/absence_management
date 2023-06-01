package com.ensah.eservice.dto.absence;

import com.ensah.eservice.models.AbsenceEtat;
import com.ensah.eservice.models.Enseignant;
import com.ensah.eservice.models.Etudiant;
import com.ensah.eservice.models.TypeSeance;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class AbsenceDTO {

    private Long id;
    private Date absenceStart;
    private Date absenceEnd;
    private AbsenceEtat etat;
    private TypeSeance typeSeance;
    private Enseignant enseignant;
    private Etudiant etudiant;

}
