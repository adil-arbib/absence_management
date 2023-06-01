package com.ensah.eservice.services.Absences;

import com.ensah.eservice.dto.absence.AbsenceMapper;
import com.ensah.eservice.repositories.AbsenceRepository;
import com.ensah.eservice.repositories.TypeSeanceRepository;
import com.ensah.eservice.services.NiveauService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AbsenceService {

    private final AbsenceRepository absenceRepository;
    private final NiveauService niveauService;
    private final TypeSeanceRepository typeSeanceRepository;
    private final AbsenceMapper absenceMapper;


}
