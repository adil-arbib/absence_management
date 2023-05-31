package com.ensah.eservice.services;


import com.ensah.eservice.dto.accreditations.AccreditationDTO;
import com.ensah.eservice.dto.accreditations.AccreditationMapper;
import com.ensah.eservice.exceptions.alreadyExists.AlreadyExistsException;
import com.ensah.eservice.exceptions.notfound.NotFoundException;
import com.ensah.eservice.models.Accreditation;
import com.ensah.eservice.models.Enseignant;
import com.ensah.eservice.repositories.AccreditationRepository;
import com.ensah.eservice.repositories.EnseignantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.NotActiveException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccreditationService {

    private final AccreditationRepository accreditationRepository;
    private final AccreditationMapper accreditationMapper;

    private final EnseignantRepository enseignantRepository;


    public List<AccreditationDTO> getAll(){
        return accreditationMapper.toListAccreditationDTO(accreditationRepository.findAll());
    }

    public Page<AccreditationDTO> getAll(int page, int size){
        return accreditationRepository.findAll(PageRequest.of(page, size)).
                map(accreditationMapper::toAccreditationDTO);
    }

    public void update(AccreditationDTO accreditationDTO, Long id) throws NotActiveException {
        Accreditation accreditation = accreditationRepository.findById(accreditationDTO.getId())
                .orElseThrow(NoSuchFieldError::new);

        Enseignant enseignant = enseignantRepository.findById(id).
                orElseThrow(NotActiveException::new);

        accreditation.setCoordinateur(enseignant);

        accreditationMapper.updateAccreditationFromDTO(accreditationDTO,accreditation);
        accreditationRepository.save(accreditation);

    }

    public AccreditationDTO findById(Long id) throws NotFoundException {
        Accreditation accreditation = accreditationRepository.findById(id)
                .orElseThrow(NotFoundException::new);

        return accreditationMapper.toAccreditationDTO(accreditation);
    }

    public AccreditationDTO create(AccreditationDTO accreditationDTO, Long id) throws AlreadyExistsException, NotFoundException {


       Accreditation accreditation =  accreditationMapper.createAccrediation(accreditationDTO);
       accreditation.setCoordinateur(enseignantRepository.findById(id).
               orElseThrow(NotFoundException::new));

       accreditationRepository.save(accreditation);
       return accreditationMapper.toAccreditationDTO(accreditation);
    }


}
