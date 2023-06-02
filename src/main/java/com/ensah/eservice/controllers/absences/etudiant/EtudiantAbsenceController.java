package com.ensah.eservice.controllers.absences.etudiant;

import com.ensah.eservice.dto.absence.AbsenceDTO;
import com.ensah.eservice.dto.reclamation.ReclamationDTO;
import com.ensah.eservice.exceptions.alreadyExists.AlreadyExistsException;
import com.ensah.eservice.exceptions.notfound.InscriptionNotFoundException;
import com.ensah.eservice.exceptions.notfound.NotFoundException;
import com.ensah.eservice.services.absences.etudiant.EtudiantAbsenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
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


   @GetMapping("/{id}/piece-justificative")
   public String showAddPieceJusPage(
           @PathVariable Long id,
           Model model,
           RedirectAttributes redirectAttributes
   ) throws NotFoundException {
      AbsenceDTO absenceDTO = etudiantAbsenceService.getAbsenceById(id);
      model.addAttribute("absenceId", absenceDTO.getId());
      if (redirectAttributes.getFlashAttributes().containsKey("successMessage")) {
         model.addAttribute("successMessage", redirectAttributes.getFlashAttributes().get("successMessage"));
      }
      return "absences/etudiant/piece-justificative";
   }


   @PostMapping("/piece-justificative")
   public RedirectView addPieceJustificative(
           @RequestParam Long absenceId,
           @RequestParam MultipartFile piece,
           RedirectAttributes redirectAttributes
           ) throws NotFoundException {

      redirectAttributes.addFlashAttribute("absenceId", absenceId);
      redirectAttributes.addFlashAttribute("successMessage", "Ajouté avec succès");

      return new RedirectView("/etudiant/absences/"+absenceId+"/piece-justificative");
   }



   @GetMapping("/{absenceId}/reclamation")
   public String showAddReclamationPage(
           @PathVariable Long absenceId,
           Model model,
           RedirectAttributes redirectAttributes
   ) {

      model.addAttribute("reclamation", new ReclamationDTO());
      model.addAttribute("absenceId", absenceId);
      if (redirectAttributes.getFlashAttributes().containsKey("message")) {
         model.addAttribute("message", redirectAttributes.getFlashAttributes().get("message"));
         System.out.println("message : "+redirectAttributes.getFlashAttributes().get("message"));
      }
      return "absences/etudiant/reclamation";
   }

   @PostMapping("/reclamation")
   public RedirectView addReclamation(
           @ModelAttribute("reclamation") ReclamationDTO reclamationDTO,
           @RequestParam("absenceId") Long absenceId,
           RedirectAttributes redirectAttributes
   ) {
      try {
         etudiantAbsenceService.addReclamation(absenceId, reclamationDTO);
         redirectAttributes.addAttribute("message", "envoyer avec succès");
      } catch (NotFoundException e) {
         redirectAttributes.addAttribute("errorMessage", "absence introuvable");
      } catch (AlreadyExistsException e) {
         redirectAttributes.addAttribute("errorMessage", e.getMessage());
      }
      return new RedirectView("/etudiant/absences/"+absenceId+"/reclamation");
   }







}
