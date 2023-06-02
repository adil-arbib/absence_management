package com.ensah.eservice.services.absences.etudiant;

import com.ensah.eservice.dto.absence.AbsenceDTO;
import com.ensah.eservice.dto.absence.AbsenceMapper;
import com.ensah.eservice.dto.reclamation.ReclamationDTO;
import com.ensah.eservice.dto.reclamation.ReclamationMapper;
import com.ensah.eservice.exceptions.notfound.InscriptionNotFoundException;
import com.ensah.eservice.exceptions.notfound.NotFoundException;
import com.ensah.eservice.models.*;
import com.ensah.eservice.repositories.AbsenceRepository;
import com.ensah.eservice.repositories.EtudiantRepository;
import com.ensah.eservice.repositories.InscriptionRepository;
import com.ensah.eservice.repositories.ReclamationRepository;
import com.ensah.eservice.services.absences.pieceJustificative.PieceJustificativeService;
import com.ensah.eservice.utils.CurrentUser;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Year;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EtudiantAbsenceService {

   private final EtudiantRepository etudiantRepository;
   private final AbsenceRepository absenceRepository;
   private final AbsenceMapper absenceMapper;
   private final InscriptionRepository inscriptionRepository;

   private final PieceJustificativeService pieceJustificativeService;

   private final ReclamationMapper reclamationMapper;

   private final ReclamationRepository reclamationRepository;


   /**
    * absences de l'etudiant dans l'année en cours
    *
    * @return
    */
   public Page<AbsenceDTO> getCurrentAbsences(int page, int size, String keyword) throws InscriptionNotFoundException {
      return getYearAbsence(Year.now().getValue(), page, size, keyword);
   }

   /**
    * absences de l'étudiant dans une année spécifique
    *
    * @param year
    * @return
    */
   public Page<AbsenceDTO> getYearAbsence(int year, int page, int size, String keyword) throws InscriptionNotFoundException {
      Etudiant currentEtudiant = (Etudiant) CurrentUser.getCurrentUser();
      Page<Absence> absencePage = absenceRepository.findByInscriptionAndElementCodeContains(
              inscriptionRepository.findByEtudiantAndAnnee(currentEtudiant, year)
                      .orElseThrow(InscriptionNotFoundException::new),
              keyword,
              PageRequest.of(page, size)
      );

      return absencePage.map(absenceMapper::toAbsenceDTO);
   }


   public void addPieceJustificative(Long absenceId, MultipartFile file) throws NotFoundException, IOException {
      Absence absence = absenceRepository.findById(absenceId)
              .orElseThrow(NotFoundException::new);
      PieceJustificative pieceJustificative = pieceJustificativeService.addPieceJustificative(file);
      absence.getPieceJustificatives().add(pieceJustificative);
      pieceJustificative.getAbsences().add(absence);
      absenceRepository.save(absence);
   }


   public void addReclamation(ReclamationDTO reclamationDTO) throws NotFoundException {
      Absence absence = absenceRepository.findById(reclamationDTO.getAbsence().getId())
              .orElseThrow(NotFoundException::new);
      Reclamation reclamation = reclamationMapper.toReclamation(reclamationDTO);
      reclamation.setAbsence(absence);
      reclamationRepository.save(reclamation);
   }

}
