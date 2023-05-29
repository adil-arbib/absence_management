package com.ensah.eservice.services.structure_pedagogique;


import com.ensah.eservice.dto.elements.ElementDTO;
import com.ensah.eservice.dto.elements.ElementMapper;
import com.ensah.eservice.exceptions.alreadyExists.AlreadyExistsException;
import com.ensah.eservice.exceptions.notfound.NotFoundException;
import com.ensah.eservice.models.Element;
import com.ensah.eservice.repositories.ElementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ElementService {

   private final ElementRepository elementRepository;
   private final ElementMapper elementMapper;


   public List<ElementDTO> getAllElements() {
      return elementMapper.toElementDTOList(elementRepository.findAll());
   }

   public ElementDTO getElementById(Long id) throws NotFoundException {
      return elementMapper.toElementDTO(
              elementRepository.findById(id).orElseThrow(NotFoundException::new)
      );
   }

   public Element create(ElementDTO elementDTO) throws AlreadyExistsException {
      if (elementRepository.existsByNomOrCode(elementDTO.getNom(), elementDTO.getCode()))
         throw new AlreadyExistsException();
      Element element = elementMapper.toElement(elementDTO);
      return elementRepository.save(element);
   }


   public void deleteElement(Long id) throws NotFoundException {
      Element element = elementRepository.findById(id).orElseThrow(NotFoundException::new);
      elementRepository.delete(element);
   }

   public void updateElement(ElementDTO elementDTO) throws NotFoundException {
      Element element = elementRepository.findById(elementDTO.getId()).orElseThrow(NotFoundException::new);
      elementMapper.updateElementFromDTO(elementDTO, element);
      elementRepository.save(element);
   }

   private Page<ElementDTO> findByNomContains(int page, int size, String keyword) {
      Page<Element> elementPage = elementRepository.findByNomContains(keyword, PageRequest.of(page, size));
      return elementPage.map(elementMapper::toElementDTO);
   }

   private Page<ElementDTO> getAll(int page, int size){
      return elementRepository.findAll(PageRequest.of(page, size))
              .map(elementMapper::toElementDTO);
   }
   public Page<ElementDTO> getElementsPage(int page, int size, String keyword){
      return keyword.isEmpty()
              ? getAll(page, size)
              : findByNomContains(page, size, keyword);
   }

}
