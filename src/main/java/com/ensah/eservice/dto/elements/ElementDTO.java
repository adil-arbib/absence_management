package com.ensah.eservice.dto.elements;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ElementDTO {

    private Long id;
    private String nom;
    private String code;

}
