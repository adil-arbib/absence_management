package com.ensah.eservice.dto.inscription;

import com.ensah.eservice.dto.TypeSeance.TypeSeanceMapper;
import com.ensah.eservice.dto.absence.AbsenceDTO;
import com.ensah.eservice.dto.elements.ElementDTO;
import com.ensah.eservice.dto.niveau.NiveauMapper;
import com.ensah.eservice.dto.users.enseignant.EnseignantMapper;
import com.ensah.eservice.dto.users.etudiant.EtudiantMapper;
import com.ensah.eservice.models.Absence;
import com.ensah.eservice.models.Inscription;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring",    uses = {
        EtudiantMapper.class,
        NiveauMapper.class,
    })
public interface InscriptionMapper {

    Inscription toInscription(InscriptionDTO inscriptionDTO);
    InscriptionDTO toInscriptionDTO(Inscription inscription);
    List<InscriptionDTO> toInscriptionDTOList(Collection<Inscription> inscriptions);
    List<Inscription> toInscriptionList(Collection<InscriptionDTO> inscriptionDTOS);
    void updateInscriptionFromDTO(InscriptionDTO inscriptionDTO, @MappingTarget Inscription inscription);

}
