package com.ensah.eservice.dto.elements;

import com.ensah.eservice.models.Element;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ElementMapper {


    Element toElement(ElementDTO elementDTO);

    List<ElementDTO> toElementDTOList(List<Element> elements);

}
