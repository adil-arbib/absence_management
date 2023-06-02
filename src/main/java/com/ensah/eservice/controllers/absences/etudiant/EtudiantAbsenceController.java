package com.ensah.eservice.controllers.absences.etudiant;

import com.ensah.eservice.dto.absence.AbsenceDTO;
import com.ensah.eservice.dto.users.etudiant.EtudiantDTO;
import com.ensah.eservice.exceptions.notfound.InscriptionNotFoundException;
import com.ensah.eservice.services.absences.etudiant.EtudiantAbsenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.logging.Logger;

@Controller
@RequestMapping("etudiant/absences")
@RequiredArgsConstructor
public class EtudiantAbsenceController {

   private final EtudiantAbsenceService etudiantAbsenceService;

   private final Logger logger = Logger.getLogger(EtudiantAbsenceController.class.getName());

   @GetMapping
   public String allEtudiants(
           @RequestParam(name = "page", defaultValue = "0") int page,
           @RequestParam(name = "size", defaultValue = "10") int size,
           @RequestParam(name = "keyword", defaultValue = "") String keyword,
           Model model) throws InscriptionNotFoundException {

      Page<AbsenceDTO> absencesPage = etudiantAbsenceService.getCurrentAbsences(page, size, keyword);
      model.addAttribute("absencesPage", absencesPage);
      model.addAttribute("pages", new int[absencesPage.getTotalPages()]);
      model.addAttribute("currentPage", page);
      model.addAttribute("keyword", keyword);
      model.addAttribute("max", absencesPage.getTotalPages() - 1);
      model.addAttribute("size", size);
      return "absences/etudiant/all";
   }

}
