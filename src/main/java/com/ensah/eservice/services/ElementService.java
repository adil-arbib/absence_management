package com.ensah.eservice.services;


import com.ensah.eservice.dto.elements.ElementDTO;
import com.ensah.eservice.dto.elements.ElementMapper;
import com.ensah.eservice.exceptions.alreadyExists.AlreadyExistsException;
import com.ensah.eservice.models.Element;
import com.ensah.eservice.repositories.ElementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ElementService {

    private final ElementRepository elementRepository;
    private final ElementMapper elementMapper;



    public Element create(ElementDTO elementDTO) throws AlreadyExistsException {
        if(elementRepository.existsByNomOrCode(elementDTO.getNom(), elementDTO.getCode()))
            throw new AlreadyExistsException();
        Element element = elementMapper.toElement(elementDTO);
        return elementRepository.save(element);
    }


    public List<ElementDTO> getAll() {
        return elementMapper.toElementDTOList(elementRepository.findAll());
    }



}
