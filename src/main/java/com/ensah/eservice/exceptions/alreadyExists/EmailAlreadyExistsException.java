package com.ensah.eservice.exceptions.alreadyExists;

public class EmailAlreadyExistsException extends AlreadyExistsException{

   public EmailAlreadyExistsException() {
      this("Email déjà utilisée");
   }
   public EmailAlreadyExistsException(String message) {
      super(message);
   }
}
