package com.ensah.eservice.dto.files;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileDTO {

   private Long id;

   private String data;

   private String type;

}
