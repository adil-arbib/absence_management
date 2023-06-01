package com.ensah.eservice.services.Absences;

import com.ensah.eservice.dto.absence.AbsenceDTO;
import com.ensah.eservice.dto.absence.AbsenceMapper;
import com.ensah.eservice.dto.inscription.InscriptionMapper;
import com.ensah.eservice.dto.niveau.NiveauDTO;
import com.ensah.eservice.dto.users.etudiant.EtudiantDTO;
import com.ensah.eservice.dto.users.etudiant.EtudiantMapper;
import com.ensah.eservice.exceptions.notfound.NotFoundException;
import com.ensah.eservice.models.Absence;
import com.ensah.eservice.models.Inscription;
import com.ensah.eservice.repositories.AbsenceRepository;
import com.ensah.eservice.services.InscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AbsenceService {

    private final AbsenceRepository absenceRepository;
    private final InscriptionService inscriptionService;
    private final AbsenceMapper absenceMapper;
    private final InscriptionMapper inscriptionMapper;
    private final EtudiantMapper etudiantMapper;

    public void create(AbsenceDTO absenceDTO){
        absenceRepository.save(absenceMapper.toAbsence(absenceDTO));
    }
    public AbsenceDTO getById(Long id) throws NotFoundException {
        return  absenceMapper.toAbsenceDTO(absenceRepository.findById(id).orElseThrow(NotFoundException::new));
    }
    public Page<AbsenceDTO> getAll(int page, int size){
        return absenceRepository.findAll(PageRequest.of(page,size)).map(absenceMapper::toAbsenceDTO);
    }
    public void delete(Long id) throws NotFoundException {
        Absence absence = absenceRepository.findById(id).orElseThrow(NotFoundException::new);
        absenceRepository.delete(absence);
    }
    public void update(AbsenceDTO absenceDTO) throws NotFoundException {
        Absence absence = absenceRepository.findById(absenceDTO.getId()).orElseThrow(NotFoundException::new);
            absenceMapper.updateAbsenceFromDTO(absenceDTO,absence);
            absenceRepository.save(absence);
    }

    public List<EtudiantDTO> getStudents(NiveauDTO niveauDTO){
        List<Inscription> list= inscriptionMapper.toInscriptionList(inscriptionService.getByNiveau(niveauDTO));
        List<EtudiantDTO> etudiantList= new ArrayList<>();
        for (Inscription inscription: list){
            etudiantList.add(etudiantMapper.toEtudiantDTO(inscription.getEtudiant()));
        }
        return etudiantList;
    }

}
