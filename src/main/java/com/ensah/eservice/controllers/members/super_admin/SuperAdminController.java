package com.ensah.eservice.controllers.members.super_admin;

import com.ensah.eservice.dto.elements.ElementDTO;
import com.ensah.eservice.dto.users.etudiant.EtudiantDTO;
import com.ensah.eservice.exceptions.alreadyExists.AlreadyExistsException;
import com.ensah.eservice.exceptions.alreadyExists.CneAlreadyExistsException;
import com.ensah.eservice.exceptions.alreadyExists.EmailAlreadyExistsException;
import com.ensah.eservice.exceptions.notfound.NotFoundException;
import com.ensah.eservice.services.members.EtudiantService;
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
import java.util.Arrays;

@Controller
@RequiredArgsConstructor
@RequestMapping("/super-admin")
public class SuperAdminController {

   private final EtudiantService etudiantService;

   @GetMapping("/etudiants")
   public String allEtudiants(
           @RequestParam(name = "page", defaultValue = "0") int page,
           @RequestParam(name = "size", defaultValue = "10") int size,
           @RequestParam(name = "keyword", defaultValue = "") String keyword,
           Model model) {
      Page<EtudiantDTO> etudiantsPage = etudiantService.findByAttributesContains(page, size, keyword);
      model.addAttribute("etudiantsPage", etudiantsPage);
      model.addAttribute("pages", new int[etudiantsPage.getTotalPages()]);
      model.addAttribute("currentPage", page);
      model.addAttribute("keyword", keyword);
      model.addAttribute("max", etudiantsPage.getTotalPages() - 1);
      model.addAttribute("size", size);
      return "etudiants/all";
   }


   @GetMapping("/etudiants/create")
   public String showCreateEtudiantPage(Model model) {
      model.addAttribute("etudiant", new EtudiantDTO());
      return "etudiants/create";
   }

   @PostMapping("/etudiants/create")
   public String createEtudiant(
           @ModelAttribute("etudiant") EtudiantDTO etudiantDTO,
           Model model
   ) {
      boolean error = false;
      try {
         etudiantService.create(etudiantDTO);
      } catch (CneAlreadyExistsException e) {
         model.addAttribute("cneError", e.getMessage());
         error = true;
      } catch (EmailAlreadyExistsException e) {
         model.addAttribute("emailError", e.getMessage());
         error = true;
      }
      if (!error) {
         model.addAttribute("successMessage", "enregistré avec succès");
         model.addAttribute("etudiant" , new EtudiantDTO());
      }
      return "etudiants/create";
   }


   @PostMapping("/etudiants/delete")
   public String deleteEtudiant(@RequestParam("id") Long id, Model model) {
      try {
         etudiantService.delete(id);
      } catch (NotFoundException e) {
         model.addAttribute("error", "");
      }
      return "redirect:/super-admin/etudiants";
   }

   @GetMapping("/etudiants/{id}")
   public String showEtudiantPage(
           @PathVariable Long id, Model model) throws NotFoundException {
      model.addAttribute("etudiant", etudiantService.getById(id));
      System.out.println(etudiantService.getById(id));
      return "etudiants/etudiant";
   }

   @PostMapping("/etudiants/update")
   public String updateEtudiant(
           @ModelAttribute("etudiant") EtudiantDTO etudiantDTO,
           @RequestParam(value = "file", required = false) MultipartFile image,
           Model model
   ) throws NotFoundException, IOException {
      boolean error = false;
      try {
         etudiantService.update(etudiantDTO, image);
      } catch (EmailAlreadyExistsException e) {
         model.addAttribute("emailError", e.getMessage());
         error = true;
      } catch (CneAlreadyExistsException e) {
         model.addAttribute("cneError", e.getMessage());
         error = true;
      }
      if(!error) {
         return "redirect:/super-admin/etudiants/"+etudiantDTO.getId();
      }
      return "etudiants/etudiant";
   }

}