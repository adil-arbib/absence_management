package com.ensah.eservice.controllers.members.super_admin;

import com.ensah.eservice.dto.elements.ElementDTO;
import com.ensah.eservice.dto.users.cadre_admin.CadreAdministrateurDTO;
import com.ensah.eservice.dto.users.enseignant.EnseignantDTO;
import com.ensah.eservice.dto.users.etudiant.EtudiantDTO;
import com.ensah.eservice.exceptions.alreadyExists.AlreadyExistsException;
import com.ensah.eservice.exceptions.alreadyExists.CneAlreadyExistsException;
import com.ensah.eservice.exceptions.alreadyExists.EmailAlreadyExistsException;
import com.ensah.eservice.exceptions.notfound.NotFoundException;
import com.ensah.eservice.services.members.CadreAdministrateurService;
import com.ensah.eservice.services.members.EnseignantService;
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

   private final EnseignantService enseignantService;

   private final CadreAdministrateurService cadreAdministrateurService;

   @GetMapping("/etudiants")
   public String allEtudiants(@RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "10") int size, @RequestParam(name = "keyword", defaultValue = "") String keyword, Model model) {
      Page<EtudiantDTO> etudiantsPage = etudiantService.findByAttributesContains(page, size, keyword);
      model.addAttribute("etudiantsPage", etudiantsPage);
      model.addAttribute("pages", new int[etudiantsPage.getTotalPages()]);
      model.addAttribute("currentPage", page);
      model.addAttribute("keyword", keyword);
      model.addAttribute("max", etudiantsPage.getTotalPages() - 1);
      model.addAttribute("size", size);
      return "super_admin/etudiants/all";
   }


   @GetMapping("/etudiants/create")
   public String showCreateEtudiantPage(Model model) {
      model.addAttribute("etudiant", new EtudiantDTO());
      return "super_admin/etudiants/create";
   }

   @PostMapping("/etudiants/create")
   public String createEtudiant(@ModelAttribute("etudiant") EtudiantDTO etudiantDTO, Model model) {
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
         model.addAttribute("etudiant", new EtudiantDTO());
      }
      return "super_admin/etudiants/create";
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
   public String showEtudiantPage(@PathVariable Long id, Model model) throws NotFoundException {
      model.addAttribute("etudiant", etudiantService.getById(id));
      System.out.println(etudiantService.getById(id));
      return "super_admin/etudiants/etudiant";
   }

   @PostMapping("/etudiants/update")
   public String updateEtudiant(@ModelAttribute("etudiant") EtudiantDTO etudiantDTO, @RequestParam(value = "file", required = false) MultipartFile image, Model model) throws NotFoundException, IOException {
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
      if (!error) {
         return "redirect:/super-admin/etudiants/" + etudiantDTO.getId();
      }
      return "super_admin/etudiants/etudiant";
   }


   @GetMapping("/etudiants/recuperations")
   public String getDeletedEtudiants(
           @RequestParam(name = "page", defaultValue = "0") int page,
           @RequestParam(name = "size", defaultValue = "10") int size,
           @RequestParam(name = "keyword", defaultValue = "") String keyword,
           Model model) {
      Page<EtudiantDTO> etudiantsPage = etudiantService.getDeletedEtudiants(page, size, keyword);
      model.addAttribute("etudiantsPage", etudiantsPage);
      model.addAttribute("pages", new int[etudiantsPage.getTotalPages()]);
      model.addAttribute("currentPage", page);
      model.addAttribute("keyword", keyword);
      model.addAttribute("max", etudiantsPage.getTotalPages() - 1);
      model.addAttribute("size", size);
      return "super_admin/etudiants/recuperations";

   }


   @PostMapping("/etudiants/recover")
   public String recover(@RequestParam("id") Long id, Model model) {
      try {
         etudiantService.recover(id);
      } catch (NotFoundException e) {
         model.addAttribute("error", "");
      }
      return "redirect:/super-admin/etudiants/recuperations";
   }


   ////////////////////////////////////////////////////////////////////////////////////


   @GetMapping("/enseignants")
   public String allEnseignants(
           @RequestParam(name = "page", defaultValue = "0") int page,
           @RequestParam(name = "size", defaultValue = "10") int size,
           @RequestParam(name = "keyword", defaultValue = "") String keyword,
           Model model) {
      Page<EnseignantDTO> enseignantsPage = enseignantService.findByAttributesContains(page, size, keyword);
      model.addAttribute("enseignantsPage", enseignantsPage);
      model.addAttribute("pages", new int[enseignantsPage.getTotalPages()]);
      model.addAttribute("currentPage", page);
      model.addAttribute("keyword", keyword);
      model.addAttribute("max", enseignantsPage.getTotalPages() - 1);
      model.addAttribute("size", size);
      return "super_admin/enseignants/all";
   }


   @GetMapping("/enseignants/create")
   public String showCreateEnseignantPage(Model model) {
      model.addAttribute("enseignant", new EnseignantDTO());
      return "super_admin/enseignants/create";
   }

   @PostMapping("/enseignants/create")
   public String createEnseignant(@ModelAttribute("enseignant") EnseignantDTO enseignantDTO, Model model) {
      boolean error = false;
      try {
         enseignantService.create(enseignantDTO);
      } catch (CneAlreadyExistsException e) {
         model.addAttribute("cinError", "cin existe déjà");
         error = true;
      } catch (EmailAlreadyExistsException e) {
         model.addAttribute("emailError", e.getMessage());
         error = true;
      }
      if (!error) {
         model.addAttribute("successMessage", "enregistré avec succès");
         model.addAttribute("enseignant", new EnseignantDTO());
      }
      return "super_admin/enseignants/create";
   }


   @PostMapping("/enseignants/delete")
   public String deleteEnseignant(@RequestParam("id") Long id, Model model) {
      try {
         enseignantService.delete(id);
      } catch (NotFoundException e) {
         model.addAttribute("error", "");
      }
      return "redirect:/super-admin/enseignants";
   }

   @GetMapping("/enseignants/{id}")
   public String showEnseignantPage(@PathVariable Long id, Model model) throws NotFoundException {
      model.addAttribute("enseignant", enseignantService.getById(id));
      return "super_admin/enseignants/enseignant";
   }


   @PostMapping("/enseignants/update")
   public String updateEnseignant(
           @ModelAttribute("enseignant") EnseignantDTO enseignantDTO,
           @RequestParam(value = "file", required = false) MultipartFile image,
           Model model) throws NotFoundException, IOException {
      boolean error = false;
      try {
         enseignantService.update(enseignantDTO, image);
      } catch (EmailAlreadyExistsException e) {
         model.addAttribute("emailError", e.getMessage());
         error = true;
      } catch (CneAlreadyExistsException e) {
         model.addAttribute("cinError", "cin existe déjà");
         error = true;
      }
      if (!error) {
         return "redirect:/super-admin/enseignants/" + enseignantDTO.getId();
      }
      return "super_admin/enseignants/enseignant";
   }

//////////////////////////////////////////////////////////////////////////////////////////


   @GetMapping("/cadres-administrateurs")
   public String allCadresAdmins(
           @RequestParam(name = "page", defaultValue = "0") int page,
           @RequestParam(name = "size", defaultValue = "10") int size,
           @RequestParam(name = "keyword", defaultValue = "") String keyword,
           Model model) {
      Page<CadreAdministrateurDTO> cadreAdminsPage = cadreAdministrateurService.findByAttributesContains(page, size, keyword);
      model.addAttribute("cadreAdminsPage", cadreAdminsPage);
      model.addAttribute("pages", new int[cadreAdminsPage.getTotalPages()]);
      model.addAttribute("currentPage", page);
      model.addAttribute("keyword", keyword);
      model.addAttribute("max", cadreAdminsPage.getTotalPages() - 1);
      model.addAttribute("size", size);
      return "super_admin/cadres_administrateurs/all";
   }


   @GetMapping("/cadres-administrateurs/create")
   public String showCreateCadreAdminPage(Model model) {
      model.addAttribute("cadreAdmin", new CadreAdministrateurDTO());
      return "super_admin/cadres_administrateurs/create";
   }

   @PostMapping("/cadres-administrateurs/create")
   public String createCadreAdmin(@ModelAttribute("cadreAdmin") CadreAdministrateurDTO cadreAdministrateurDTO, Model model) {
      boolean error = false;
      try {
         cadreAdministrateurService.create(cadreAdministrateurDTO);
      } catch (EmailAlreadyExistsException e) {
         model.addAttribute("emailError", e.getMessage());
         error = true;
      } catch (CneAlreadyExistsException e) {
         model.addAttribute("cinError", "cin existe déjà");
         error = true;
      }
      if (!error) {
         model.addAttribute("successMessage", "enregistré avec succès");
         model.addAttribute("cadreAdmin", new CadreAdministrateurDTO());
      }
      return "super_admin/cadres_administrateurs/create";
   }


   @PostMapping("/cadres-administrateurs/delete")
   public String deleteCadreAdmin(@RequestParam("id") Long id) {
      try {
         cadreAdministrateurService.delete(id);
      } catch (NotFoundException ignored) {
      }
      return "redirect:/super-admin/cadres-administrateurs";
   }

   @GetMapping("/cadres-administrateurs/{id}")
   public String showCadreAdminPage(@PathVariable Long id, Model model) throws NotFoundException {
      model.addAttribute("cadreAdmin", cadreAdministrateurService.getById(id));
      return "super_admin/cadres_administrateurs/cadreAdmin";
   }


   @PostMapping("/cadres-administrateurs/update")
   public String updateCadreAdmin(
           @ModelAttribute("cadreAdmin") CadreAdministrateurDTO cadreAdministrateurDTO,
           @RequestParam(value = "file", required = false) MultipartFile image,
           Model model) throws NotFoundException, IOException {
      boolean error = false;
      try {
         cadreAdministrateurService.update(cadreAdministrateurDTO, image);
      } catch (EmailAlreadyExistsException e) {
         model.addAttribute("emailError", e.getMessage());
         error = true;
      } catch (CneAlreadyExistsException e) {
         model.addAttribute("cinError", "cin existe déjà");
         error = true;
      }
      if (!error) {
         return "redirect:/super-admin/cadres-administrateurs/" + cadreAdministrateurDTO.getId();
      }
      return "super_admin/cadres_administrateurs/cadreAdmin";
   }

///////////////////////////////////////////////////////////////////


   @GetMapping("/comptes/create")
   public String showCreateComptePage() {
      return "super_admin/comptes/create";
   }

   @PostMapping("/comptes/create")
   public String selectedRole(
           @RequestParam("role") String role,
           @RequestParam(value = "cin", required = false) String cin,
           @RequestParam(value = "cne", required = false) String cne
   ) {
      System.out.println(role);
      System.out.println(cne);
      System.out.println(cin);
      return "redirect:/super-admin/comptes/create";
   }

}