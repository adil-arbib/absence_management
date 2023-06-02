package com.ensah.eservice.exceptions.notfound;

public class InscriptionNotFoundException extends NotFoundException{

   public InscriptionNotFoundException() {
      this("Inscription introuvable");
   }

   public InscriptionNotFoundException(String message) {
      super(message);
   }
}
