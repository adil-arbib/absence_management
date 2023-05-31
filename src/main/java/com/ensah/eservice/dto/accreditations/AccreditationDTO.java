package com.ensah.eservice.dto.accreditations;

import com.ensah.eservice.dto.users.UserDTO;
import com.ensah.eservice.models.Enseignant;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class AccreditationDTO {

    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date debutAccreditation;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date finAccreditation;

    private UserDTO coordinateur;

}
