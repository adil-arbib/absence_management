package com.ensah.eservice.dto.users.comptes;

import com.ensah.eservice.dto.users.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompteDTO {

   private Long id;

   private String username;

   private String password;

   private boolean enabled;

   private boolean accountNotExpired;

   private boolean accountNotLocked;

   private boolean disconnectAccount;

   private UserDTO utilisateur;

}
