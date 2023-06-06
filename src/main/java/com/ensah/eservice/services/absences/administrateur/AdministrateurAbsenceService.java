package com.ensah.eservice.services.absences.administrateur;


import com.ensah.eservice.dto.TypeSeance.TypeSeanceMapper;
import com.ensah.eservice.dto.absence.AbsenceDTO;
import com.ensah.eservice.dto.absence.AbsenceMapper;
import com.ensah.eservice.dto.elements.ElementDTO;
import com.ensah.eservice.dto.elements.ElementMapper;
import com.ensah.eservice.dto.inscription.InscriptionMapper;
import com.ensah.eservice.dto.niveau.NiveauDTO;
import com.ensah.eservice.dto.niveau.NiveauMapper;
import com.ensah.eservice.dto.users.enseignant.EnseignantMapper;
import com.ensah.eservice.dto.users.etudiant.EtudiantDTO;
import com.ensah.eservice.dto.users.etudiant.EtudiantMapper;
import com.ensah.eservice.exceptions.notfound.InscriptionNotFoundException;
import com.ensah.eservice.exceptions.notfound.NotFoundException;
import com.ensah.eservice.models.*;
import com.ensah.eservice.repositories.*;
import com.ensah.eservice.services.InscriptionService;
import com.ensah.eservice.services.NiveauService;
import com.ensah.eservice.services.absences.AbsenceService;
import com.ensah.eservice.services.absences.pieceJustificative.PieceJustificativeService;
import com.ensah.eservice.services.members.EnseignantService;
import com.ensah.eservice.services.members.EtudiantService;
import com.ensah.eservice.services.structure_pedagogique.ElementService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.crypto.dsig.spec.ExcC14NParameterSpec;
import java.io.IOException;
import java.time.Year;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdministrateurAbsenceService {

    private final NiveauService niveauService;
    private final AbsenceService absenceService;
    private final EnseignantService enseignantService;
    private final ElementService elementService;
    private final AbsenceRepository absenceRepository;
    private final InscriptionService inscriptionService;
    private final InscriptionRepository inscriptionRepository;
    private final InscriptionMapper inscriptionMapper;
    private final EtudiantRepository etudiantRepository;
    private final EtudiantMapper etudiantMapper;
    private final EtudiantService etudiantService;
    private final PieceJustificativeService pieceJustificativeService;
    private final TypeSeanceRepository typeSeanceRepository;
    private final AbsenceMapper absenceMapper;
    private final EnseignantMapper enseignantMapper;
    private final ElementMapper elementMapper;
    private final TypeSeanceMapper typeSeanceMapper;
    private final NiveauMapper niveauMapper;



    public Page<AbsenceDTO> getEtudiantAbsences(EtudiantDTO etudiantDTO, String elementName, int page, int size) throws NotFoundException {
        Etudiant etudiant = etudiantMapper.toEtudiant(etudiantService.getById(etudiantDTO.getId()));

        Page<Absence> absencePage = absenceRepository.findByInscriptionAndElementNomContains(inscriptionRepository.
                findByEtudiantAndAnnee(etudiant, Year.now().getValue()).orElseThrow(NotFoundException::new),
                elementName,
                PageRequest.of(page, size));

        return absencePage.map(absenceMapper::toAbsenceDTO);
    }

    public List<EtudiantDTO> getEtudiantsByNiveauAndName(NiveauDTO niveauDTO, String keyword) throws NotFoundException {

        Niveau niveau = niveauMapper.createNiveau(niveauService.getNiveauById(niveauDTO.getId()));

        List<Inscription> inscriptionList = inscriptionRepository.
                findAllByAnneeAndNiveauAndEtudiantNomContains(Year.now().getValue(), niveau, keyword);


        return  inscriptionList.stream().map(Inscription::getEtudiant).
                toList().stream().map(etudiantMapper::toEtudiantDTO).collect(Collectors.toList());

    }


    public List<EtudiantDTO> getEtudiantsByNiveau(NiveauDTO niveauDTO) throws NotFoundException {

        Niveau niveau = niveauMapper.createNiveau(niveauService.getNiveauById(niveauDTO.getId()));

        List<Inscription> inscriptionList = inscriptionRepository.
                findAllByAnneeAndNiveau(Year.now().getValue(), niveau);


        return  inscriptionList.stream().map(Inscription::getEtudiant).
                toList().stream().map(etudiantMapper::toEtudiantDTO).collect(Collectors.toList());

    }

    public List<AbsenceDTO> createAbsence(
            Long elementId,
            Long enseignantId,
            Date absenceStart,
            Date absenceEnd,
            String etudiantsCNE,
            Long typeSeanceId
    ) throws NotFoundException {

        String[] cne = etudiantsCNE.split(",");
        Enseignant enseignant =  enseignantMapper.toEnseignant(enseignantService.
                getById(enseignantId));
        Element element = elementMapper.toElement(elementService.getElementById(elementId));
        TypeSeance typeSeance = typeSeanceRepository.findById(typeSeanceId).
                orElseThrow(NotFoundException::new);

        List<AbsenceDTO> absenceList = new ArrayList<>();


        for (String CNE : cne){

            Inscription inscription = inscriptionRepository.
                    findByEtudiantAndAnnee(etudiantRepository.findByCne(CNE).
                    orElseThrow(NotFoundException::new), Year.now().getValue()).
                    orElseThrow(NotFoundException::new);


            Absence absence = new Absence();
            absence.setAbsenceStart(absenceStart);
            absence.setAbsenceEnd(absenceEnd);
            absence.setEnseignant(enseignant);
            absence.setElement(element);
            absence.setInscription(inscription);
            absence.setTypeSeance(typeSeance);
            absence.setEtat(AbsenceEtat.NON_JUSTIFIEE);

//            AbsenceDTO absenceDTO = absenceMapper.toAbsenceDTO(absence);
//            absenceDTO.setEtudiant(etudiantMapper.toEtudiantDTO(inscription.getEtudiant()));
            absenceList.add(absenceMapper.toAbsenceDTO(absence));
            System.out.println("inscription est :" + inscription);

            absenceRepository.save(absence);
        }

        return absenceList;
    }

    public List<EtudiantDTO> showProvidedEtudiantList( String etudiantsCNE) throws NotFoundException {

        String[] cne = etudiantsCNE.split(",");
        List<EtudiantDTO> etudiantsList = new ArrayList<>();

        for (String CNE : cne){
            etudiantsList.add(etudiantMapper.toEtudiantDTO(etudiantRepository.findByCne(CNE).
                    orElseThrow(NotFoundException::new)));

        }

        return etudiantsList;
    }

    public void updateAbsence(AbsenceDTO absenceDTO) throws NotFoundException {
        Absence absence = absenceRepository.findById(absenceDTO.getId()).
                orElseThrow(NotFoundException::new);

        absence.setId(absence.getId());
        absence.setAbsenceStart(absenceDTO.getAbsenceStart());
        absence.setAbsenceEnd(absenceDTO.getAbsenceEnd());
        absence.setInscription(inscriptionMapper.toInscription(absenceDTO.getInscription()));
        absence.setElement(elementMapper.toElement(absenceDTO.getElement()));
        absence.setEnseignant(enseignantMapper.toEnseignant(absenceDTO.getEnseignant()));
        absence.setEtat(absenceDTO.getEtat());
        absence.setTypeSeance(typeSeanceMapper.toTypeSeance(absenceDTO.getTypeSeance()));


//        absenceMapper.updateAbsenceFromDTO(absenceDTO, absence);

        System.out.println("elements"+absence.getElement());
        absenceRepository.save(absence);

    }

    public void deleteAbsence(Long id) throws NotFoundException {
        Absence absence = absenceRepository.findById(id).
                orElseThrow(NotFoundException::new);

        absenceRepository.delete(absence);
    }

    public void attachPieceJustificative(Long absenceId, MultipartFile file) throws NotFoundException, IOException {
        Absence absence = absenceRepository.findById(absenceId)
                .orElseThrow(NotFoundException::new);
        PieceJustificative pieceJustificative = pieceJustificativeService.addPieceJustificative(file);
        absence.getPieceJustificatives().add(pieceJustificative);
        absenceRepository.save(absence);
    }

}
