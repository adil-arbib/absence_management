package com.ensah.eservice.utils;

import com.ensah.eservice.models.Compte;
import com.ensah.eservice.models.Utilisateur;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class CurrentUser {

   public static Compte currentUserAccount() {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      if(!authentication.isAuthenticated()) return null;
      return (Compte) authentication.getPrincipal();
   }


   public static Utilisateur getCurrentUser() {
      if(currentUserAccount() == null) return null;
      return currentUserAccount().getUtilisateur();
   }

}
