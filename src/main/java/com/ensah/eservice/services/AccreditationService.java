package com.ensah.eservice.services;


import com.ensah.eservice.dto.accreditations.AccreditationDTO;
import com.ensah.eservice.dto.accreditations.AccreditationMapper;
import com.ensah.eservice.repositories.AccreditationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccreditationService {

    private final AccreditationRepository accreditationRepository;
    private final AccreditationMapper accreditationMapper;


    public List<AccreditationDTO> getAll(){
        return accreditationMapper.toListAccreditationDTO(accreditationRepository.findAll());
    }

    public Page<AccreditationDTO> getAll(int page, int size){
        return accreditationRepository.findAll(PageRequest.of(page, size)).
                map(accreditationMapper::toAccreditationDTO);
    }
}
