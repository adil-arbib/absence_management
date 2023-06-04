package com.ensah.eservice.services.absences;

import com.ensah.eservice.dto.absence.AbsenceDTO;
import com.ensah.eservice.dto.absence.AbsenceMapper;
import com.ensah.eservice.exceptions.notfound.NotFoundException;
import com.ensah.eservice.models.Absence;
import com.ensah.eservice.repositories.AbsenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AbsenceService {

    private final AbsenceRepository absenceRepository;
    private final AbsenceMapper absenceMapper;


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
//    public void update(AbsenceDTO absenceDTO) throws NotFoundException {
//        Absence absence = absenceRepository.findById(absenceDTO.getId()).orElseThrow(NotFoundException::new);
//            absenceMapper.updateAbsenceFromDTO(absenceDTO,absence);
//            absenceRepository.save(absence);
//    }



}
