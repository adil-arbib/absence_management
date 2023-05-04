package com.ensah.eservice.models;

import org.springframework.security.core.GrantedAuthority;

public enum Role  implements GrantedAuthority{
    CADRE_ADMINISTRATEUR,
    ENSEIGNANT,
    ETUDIANT,
    SUPER_ADMIN;

    @Override
    public String getAuthority() {
        return this.toString();
    }
}
