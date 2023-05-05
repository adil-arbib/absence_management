package com.ensah.eservice.dto.accreditations;

import com.ensah.eservice.dto.users.UserDTO;
import com.ensah.eservice.models.Enseignant;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class AccreditationDTO {

    private Long id;

    private Date debutAccreditation;

    private Date finAccreditation;

    private UserDTO coordinateur;

}
