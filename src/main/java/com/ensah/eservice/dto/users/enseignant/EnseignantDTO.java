package com.ensah.eservice.dto.users.enseignant;

import com.ensah.eservice.dto.users.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class EnseignantDTO extends UserDTO {

   private String cin;

}
