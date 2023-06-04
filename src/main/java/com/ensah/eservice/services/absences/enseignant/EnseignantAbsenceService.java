package com.ensah.eservice.services.absences.enseignant;

import com.ensah.eservice.dto.TypeSeance.TypeSeanceDTO;
import com.ensah.eservice.dto.absence.AbsenceDTO;
import com.ensah.eservice.dto.absence.AbsenceMapper;
import com.ensah.eservice.dto.elements.ElementDTO;
import com.ensah.eservice.dto.inscription.InscriptionMapper;
import com.ensah.eservice.dto.niveau.NiveauDTO;
import com.ensah.eservice.dto.users.enseignant.EnseignantDTO;
import com.ensah.eservice.dto.users.enseignant.EnseignantMapper;
import com.ensah.eservice.dto.users.etudiant.EtudiantDTO;
import com.ensah.eservice.dto.users.etudiant.EtudiantMapper;
import com.ensah.eservice.models.Absence;
import com.ensah.eservice.models.AbsenceEtat;
import com.ensah.eservice.models.Enseignant;
import com.ensah.eservice.models.Inscription;
import com.ensah.eservice.repositories.AbsenceRepository;
import com.ensah.eservice.services.InscriptionService;
import com.ensah.eservice.services.absences.AbsenceService;
import com.ensah.eservice.utils.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EnseignantAbsenceService {

    private final InscriptionMapper inscriptionMapper;
    private final InscriptionService inscriptionService;
    private final EtudiantMapper etudiantMapper;
    private final EnseignantMapper enseignantMapper;
    private final AbsenceRepository absenceRepository;
    private final AbsenceMapper absenceMapper;
    private final AbsenceService absenceService;

    public List<EtudiantDTO> getStudents(NiveauDTO niveauDTO){
        List<Inscription> list= inscriptionMapper.toInscriptionList(inscriptionService.getByNiveau(niveauDTO));
        List<EtudiantDTO> etudiantList= new ArrayList<>();
        for (Inscription inscription: list){
            etudiantList.add(etudiantMapper.toEtudiantDTO(inscription.getEtudiant()));
        }
        return etudiantList;
    }

     public Page<AbsenceDTO> getAllAbsences(int page, int size, String keyword)  {
      Long currentEnseignantId =  Objects.requireNonNull(CurrentUser.getCurrentUser()).getId();
      Page<Absence> absencePage = absenceRepository.findByEnseignant_Id(currentEnseignantId,keyword,
              PageRequest.of(page, size));
      return absencePage.map(absenceMapper::toAbsenceDTO);
   }
//    public void addAbsence(Date absenceStart, Date absenceEnd, ElementDTO elementDTO,
//                           TypeSeanceDTO typeSeanceDTO, List<EtudiantDTO> list){
//        Enseignant currentEnseignant = (Enseignant) CurrentUser.getCurrentUser();
//        EnseignantDTO enseignantDTO = enseignantMapper.toEnseignantDTO(currentEnseignant);
//        for ( EtudiantDTO etudiantDTO: list) {
//            AbsenceDTO absenceDTO=new AbsenceDTO(null,absenceStart,absenceEnd, AbsenceEtat.NON_JUSTIFIEE,elementDTO
//                    ,typeSeanceDTO,enseignantDTO,etudiantDTO);
//            absenceService.create(absenceDTO);
//        }
//    }

}
