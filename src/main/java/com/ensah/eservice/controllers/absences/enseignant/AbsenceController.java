package com.ensah.eservice.controllers.absences.enseignant;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
