package com.ensah.eservice.dto.files;

import com.ensah.eservice.models.File;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Base64;

@Mapper(componentModel = "spring")
public interface FileMapper {


   @Mapping(source = "data", target = "data", qualifiedByName = "convertByteArrayToString")
   FileDTO fileToFileDTO(File file);


   @Named("convertByteArrayToString")
   default String convertByteArrayToString(byte[] data) {
      return Base64.getEncoder().encodeToString(data);
   }

}
