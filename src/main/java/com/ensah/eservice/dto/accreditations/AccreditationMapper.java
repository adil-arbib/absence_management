package com.ensah.eservice.dto.accreditations;


import com.ensah.eservice.dto.files.FileMapper;
import com.ensah.eservice.models.Accreditation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring",uses = {FileMapper.class})
public interface AccreditationMapper {

//    @Mapping(target = "debutAccreditation",ignore = true)
//    @Mapping(target = "finAccreditation",ignore = true)
//    @Mapping(target = "image", ignore = true)
    @Mapping(target = "coordinateur",ignore = true)
    Accreditation createAccrediation(AccreditationDTO accreditationDTO);

//    @Mapping(target = "debutAccreditation",ignore = true)
//    @Mapping(target = "finAccreditation",ignore = true)
//    @Mapping(target = "coordinateur",ignore = true)
    AccreditationDTO toAccreditationDTO(Accreditation accreditation);

    List<AccreditationDTO> toListAccreditationDTO(List<Accreditation> accreditationList);

    @Mapping(target = "coordinateur",ignore = true)
    void updateAccreditationFromDTO(AccreditationDTO accreditationDTO,
                                    @MappingTarget Accreditation accreditation);


}
