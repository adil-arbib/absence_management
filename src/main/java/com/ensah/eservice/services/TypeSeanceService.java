package com.ensah.eservice.services;


import com.ensah.eservice.dto.TypeSeance.TypeSeanceDTO;
import com.ensah.eservice.dto.TypeSeance.TypeSeanceMapper;
import com.ensah.eservice.exceptions.alreadyExists.AlreadyExistsException;
import com.ensah.eservice.exceptions.notfound.NotFoundException;
import com.ensah.eservice.models.TypeSeance;
import com.ensah.eservice.repositories.TypeSeanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class TypeSeanceService {

    private final TypeSeanceRepository typeSeanceRepository;
    private final TypeSeanceMapper typeSeanceMapper;

    public List<TypeSeanceDTO> getAllTypeSeance(){
        return typeSeanceMapper.toTypeSeanceDTOList(typeSeanceRepository.findAll());
    }
    public TypeSeance addTypeSeance(TypeSeanceDTO typeSeanceDTO) throws AlreadyExistsException {
        if (typeSeanceRepository.existsByIntituleOrAlias(typeSeanceDTO.getIntitule(),typeSeanceDTO.getAlias()))
            throw new AlreadyExistsException();
        TypeSeance typeSeance = typeSeanceMapper.toTypeSeance(typeSeanceDTO);
        return typeSeanceRepository.save(typeSeance);
    }

    public TypeSeanceDTO getTypeSeance(Long id) throws NotFoundException {
        return typeSeanceMapper.toTypeSeanceDTO(typeSeanceRepository.
                findById(id).orElseThrow(NotFoundException::new));
    }


}
