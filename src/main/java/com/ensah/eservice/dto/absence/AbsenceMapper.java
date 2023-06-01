package com.ensah.eservice.dto.absence;

import com.ensah.eservice.models.Absence;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AbsenceMapper {
    Absence toAbsence(AbsenceDTO absenceDTO);
    AbsenceDTO toAbsenceDTO(Absence absence);
    List<AbsenceDTO> toAbsenceDTOList(Collection<Absence> absences);
    void updateAbsenceFromDTO(AbsenceDTO absenceDTO, @MappingTarget Absence absence);

}
