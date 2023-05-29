package com.ensah.eservice.dto.niveau;


import com.ensah.eservice.models.Module;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NiveauDTO {

    private Long id;

    private String alias;

    private String titre;

    private Collection<Module> modules;
}
