package com.ensah.eservice.dto.elements;

import com.ensah.eservice.models.Element;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ElementMapper {


    Element toElement(ElementDTO elementDTO);

    ElementDTO toElementDTO(Element element);
    List<ElementDTO> toElementDTOList(Collection<Element> elements);

    void updateElementFromDTO(ElementDTO elementDTO, @MappingTarget Element element);

}
