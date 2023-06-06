package com.ensah.eservice.services.absences.enseignant;

import com.ensah.eservice.dto.TypeSeance.TypeSeanceDTO;
import com.ensah.eservice.dto.TypeSeance.TypeSeanceMapper;
import com.ensah.eservice.dto.absence.AbsenceDTO;
import com.ensah.eservice.dto.absence.AbsenceMapper;
import com.ensah.eservice.dto.elements.ElementDTO;
import com.ensah.eservice.dto.elements.ElementMapper;
import com.ensah.eservice.dto.inscription.InscriptionMapper;
import com.ensah.eservice.dto.niveau.NiveauDTO;
import com.ensah.eservice.dto.niveau.NiveauMapper;
import com.ensah.eservice.dto.users.enseignant.EnseignantDTO;
import com.ensah.eservice.dto.users.enseignant.EnseignantMapper;
import com.ensah.eservice.dto.users.etudiant.EtudiantDTO;
import com.ensah.eservice.dto.users.etudiant.EtudiantMapper;
import com.ensah.eservice.exceptions.notfound.NotFoundException;
import com.ensah.eservice.models.*;
import com.ensah.eservice.repositories.AbsenceRepository;
import com.ensah.eservice.repositories.InscriptionRepository;
import com.ensah.eservice.services.NiveauService;
import com.ensah.eservice.services.TypeSeanceService;
import com.ensah.eservice.services.absences.AbsenceService;
import com.ensah.eservice.services.members.EtudiantService;
import com.ensah.eservice.services.structure_pedagogique.ElementService;
import com.ensah.eservice.utils.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnseignantAbsenceService {

    private final InscriptionRepository inscriptionRepository;
    private final EtudiantMapper etudiantMapper;
    private final InscriptionMapper inscriptionMapper;
    private final AbsenceRepository absenceRepository;
    private final AbsenceMapper absenceMapper;
    private final EnseignantMapper enseignantMapper;
    private final NiveauMapper niveauMapper;
    private final NiveauService niveauService;
    private final AbsenceService absenceService;
    private final EtudiantService etudiantService;



    public List<EtudiantDTO> getEtudiantsByNiveau(NiveauDTO niveauDTO) throws NotFoundException {

        Niveau niveau = niveauMapper.createNiveau(niveauService.getNiveauById(niveauDTO.getId()));

        List<Inscription> inscriptionList = inscriptionRepository.
                findAllByAnneeAndNiveau(Year.now().getValue(), niveau);

        return  inscriptionList.stream().map(Inscription::getEtudiant).
                toList().stream().map(etudiantMapper::toEtudiantDTO).collect(Collectors.toList());

    }

    public void createAbsence(ElementDTO elementDTO, Date absenceStart,
            Date absenceEnd, List<Long> etudiantIds, TypeSeanceDTO typeSeanceDTO
    ) throws NotFoundException {

        Enseignant enseignant=(Enseignant) CurrentUser.getCurrentUser();
        for (Long id : etudiantIds){
            Inscription inscription = inscriptionRepository.findByEtudiantAndAnnee(
                     etudiantMapper.toEtudiant(etudiantService.getById(id)),
                            Year.now().getValue()).
                    orElseThrow(NotFoundException::new);
           AbsenceDTO absenceDTO= new AbsenceDTO(null,absenceStart,absenceEnd,AbsenceEtat.NON_JUSTIFIEE,
                    elementDTO,typeSeanceDTO,enseignantMapper.toEnseignantDTO(enseignant)
                   ,inscriptionMapper.toInscriptionDTO(inscription));
            absenceService.create(absenceDTO);
        }
    }

    public List<AbsenceDTO> getAll(){
        Long enseignantId= Objects.requireNonNull(CurrentUser.getCurrentUser()).getId();
        List<AbsenceDTO> list= absenceMapper.toAbsenceDTOList(absenceRepository.findAllByEnseignant_Id(enseignantId));
        return list;
    }
}
