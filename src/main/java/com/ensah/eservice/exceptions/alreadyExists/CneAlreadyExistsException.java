package com.ensah.eservice.exceptions.alreadyExists;

public class CneAlreadyExistsException extends AlreadyExistsException{

   public CneAlreadyExistsException() {
      this("cne déjà existe");
   }

   public CneAlreadyExistsException(String message) {
      super(message);
   }
}
