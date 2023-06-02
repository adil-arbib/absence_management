package com.ensah.eservice.dto.reclamation;


import com.ensah.eservice.dto.absence.AbsenceDTO;
import com.ensah.eservice.dto.message.MessageDTO;
import lombok.*;
import org.springframework.aop.target.LazyInitTargetSource;
import org.springframework.context.support.MessageSourceAccessor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ReclamationDTO extends MessageDTO {

   private AbsenceDTO absence;

   private List<MessageDTO> responses;

}
