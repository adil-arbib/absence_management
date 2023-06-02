package com.ensah.eservice.dto.message;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {

   private Long id;

   private String title;

   private String content;

   private Date createdAt;

}
