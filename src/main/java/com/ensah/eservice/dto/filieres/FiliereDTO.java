package com.ensah.eservice.dto.filieres;


import com.ensah.eservice.dto.accreditations.AccreditationDTO;
import com.ensah.eservice.dto.users.UserDTO;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Date;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class FiliereDTO {

    private Long id;

    private String nom;

    private String alias;

    private Date createAt;

    private Collection<AccreditationDTO> accreditations;

    private UserDTO coordinateur;

}


