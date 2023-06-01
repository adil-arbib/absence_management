package com.ensah.eservice.controllers.absences.enseignant;


import com.ensah.eservice.dto.TypeSeance.TypeSeanceDTO;
import com.ensah.eservice.dto.files.FileDTO;
import com.ensah.eservice.dto.modules.ModuleDTO;
import com.ensah.eservice.dto.niveau.NiveauDTO;
import com.ensah.eservice.dto.users.etudiant.EtudiantDTO;
import com.ensah.eservice.exceptions.notfound.NotFoundException;
import com.ensah.eservice.repositories.InscriptionRepository;
import com.ensah.eservice.services.Absences.AbsenceService;
import com.ensah.eservice.services.FiliereService;
import com.ensah.eservice.services.NiveauService;
import com.ensah.eservice.services.TypeSeanceService;
import com.ensah.eservice.services.structure_pedagogique.ModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;

@Controller
@RequestMapping("enseignant/absences")
@RequiredArgsConstructor
public class AbsenceController {
//
//    private final AbsenceService absenceService;
//    private final NiveauService niveauService;
//    private final InscriptionRepository inscriptionRepository;
//    private final FiliereService filiereService;
//    private final ModuleService moduleService;
//    private final TypeSeanceService typeSeanceService;

//
//    @GetMapping("/saisirAbsence")
//    public String getFormAbsence(Model model){
//        model.addAttribute("filieres",filiereService.getAll() );
//        model.addAttribute("modules",moduleService.getAll());
//        model.addAttribute("typeSeances",typeSeanceService.getAllTypeSeance());
//        return "enseignant/AddAbsences";
//    }

//    @PostMapping("/saisirAbsences")
//    public String addAbsence(@RequestParam("filiere") Long filiereId,
//                             @RequestParam("niveau") Long niveauId,
//                             @RequestParam("moduleId") Long moduleId,
//                             @RequestParam("typeSelected") Long typeSeanceId,
//                             Model model) throws NotFoundException {
//        model.addAttribute("filiere",filiereService.getFiliereById(filiereId));
//        model.addAttribute("niveau",niveauService.getNiveauById(niveauId));
//        model.addAttribute("module",moduleService.getModuleById(moduleId));
//        model.addAttribute("typeSeance",typeSeanceService.getTypeSeance(typeSeanceId));
//        model.addAttribute("etudiants",inscriptionRepository.findAllByAnneeAndNiveau(new Date()
//             ,niveauService.getNiveauById(niveauId)));
//        return "enseignant/listeStudents";
//    }

}
