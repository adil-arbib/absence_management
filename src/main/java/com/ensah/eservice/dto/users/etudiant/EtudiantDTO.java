package com.ensah.eservice.dto.users.etudiant;

import com.ensah.eservice.dto.users.UserDTO;
import lombok.*;
import java.sql.Date;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class EtudiantDTO extends UserDTO {

   private String cne;

   private Date dateNaissance;



}
