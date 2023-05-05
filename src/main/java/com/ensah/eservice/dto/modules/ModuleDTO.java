package com.ensah.eservice.dto.modules;

import com.ensah.eservice.dto.elements.ElementDTO;
import com.ensah.eservice.models.Element;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ModuleDTO {

    private Long id;
    private String nom;
    private String code;

    private Collection<ElementDTO> elements;

}
