package com.ensah.eservice.dto.filieres;

import com.ensah.eservice.models.Accreditation;
import com.ensah.eservice.models.Niveau;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Collection;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class FiliereDTO {

    private Long id;

    private String nom;

    private String alias;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createAt;


    private Collection<Accreditation> accreditations;


    private Collection<Niveau> niveaux;
}
