package com.ensah.eservice.controllers.members.etudiants;

import com.ensah.eservice.services.members.EtudiantService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/members/etudiants")
@RequiredArgsConstructor
public class EtudiantController {

   private final EtudiantService etudiantService;

   @GetMapping
   public String allEtudiants(
           @RequestParam(name = "page", defaultValue = "0") int page,
           @RequestParam(name = "size", defaultValue = "10") int size,
           @RequestParam(name = "keyword", defaultValue = "") String keyword,
           Model model) {
//      model.addAttribute("etudiants", etudiantService.getAll(page, size));
      return "etudiants/all";
   }

}
