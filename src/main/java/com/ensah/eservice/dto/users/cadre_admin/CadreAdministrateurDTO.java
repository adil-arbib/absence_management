package com.ensah.eservice.dto.users.cadre_admin;

import com.ensah.eservice.dto.users.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CadreAdministrateurDTO extends UserDTO {

   private String cin;

}
