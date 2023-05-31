package com.ensah.eservice.utils;

import java.util.UUID;

public final class PasswordGenerator {

   public static String generateRandomPassword() {
      UUID uuid = UUID.randomUUID();
      return uuid.toString().replace("-", "").substring(0, 8);
   }

}
