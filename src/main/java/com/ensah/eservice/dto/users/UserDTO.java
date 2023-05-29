package com.ensah.eservice.dto.users;

import com.ensah.eservice.dto.files.FileDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;

    private String nom;

    private String prenom;

    private String nomArabe;

    private String prenomArab;

    private String email;

    private String tel;

    private FileDTO image;


}
