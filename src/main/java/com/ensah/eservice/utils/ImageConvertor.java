package com.ensah.eservice.utils;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public final class ImageConvertor {


   public static String convertToImage(byte[] imgArray) {
      return java.util.Base64.getEncoder().encodeToString(imgArray);
   }


   public static byte[] convertToBytes(MultipartFile file) throws IOException {
      return file.getBytes();
   }



   public static String generateName() {
      return UUID.randomUUID().toString();
   }



}
