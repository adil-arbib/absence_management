package com.ensah.eservice.dto.message;

import com.ensah.eservice.models.Message;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessageMapper {

   Message toMessage(MessageDTO messageDTO);

   MessageDTO toMessageDTO(Message message);

}
