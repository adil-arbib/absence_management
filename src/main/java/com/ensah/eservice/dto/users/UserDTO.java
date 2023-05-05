package com.ensah.eservice.dto.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserDTO {

    private Long id;

    private String nom;

    private String prenom;

    private byte[] image;

}
