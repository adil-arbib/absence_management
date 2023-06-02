package com.ensah.eservice.controllers.absences.etudiant;

import com.ensah.eservice.dto.absence.AbsenceDTO;
import com.ensah.eservice.dto.reclamation.ReclamationDTO;
import com.ensah.eservice.exceptions.notfound.InscriptionNotFoundException;
import com.ensah.eservice.services.absences.etudiant.EtudiantAbsenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("etudiant/reclamations")
@RequiredArgsConstructor
public class EtudiantReclamationController {

   private final EtudiantAbsenceService etudiantAbsenceService;


   @GetMapping
   public String allReclamations(
           @RequestParam(name = "page", defaultValue = "0") int page,
           @RequestParam(name = "size", defaultValue = "10") int size,
           Model model) {

      Page<ReclamationDTO> reclamationsPage = etudiantAbsenceService.getCurrentEtudiantReclamations(page, size);
      model.addAttribute("reclamationsPage", reclamationsPage);
      model.addAttribute("pages", new int[reclamationsPage.getTotalPages()]);
      model.addAttribute("currentPage", page);
      model.addAttribute("max", reclamationsPage.getTotalPages() - 1);
      model.addAttribute("size", size);
      return "reclamations/etudiant/all";
   }


}
