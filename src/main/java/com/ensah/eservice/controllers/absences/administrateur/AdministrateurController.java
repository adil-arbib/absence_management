package com.ensah.eservice.controllers.absences.administrateur;


import com.ensah.eservice.dto.absence.AbsenceDTO;
import com.ensah.eservice.exceptions.notfound.NotFoundException;
import com.ensah.eservice.services.FiliereService;
import com.ensah.eservice.services.NiveauService;
import com.ensah.eservice.services.TypeSeanceService;
import com.ensah.eservice.services.absences.administrateur.AdministrateurAbsenceService;
import com.ensah.eservice.services.members.EnseignantService;
import com.ensah.eservice.services.members.EtudiantService;
import com.ensah.eservice.services.structure_pedagogique.ElementService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequiredArgsConstructor
@RequestMapping("/administrateur/absences")
public class AdministrateurController {

    private final AdministrateurAbsenceService administrateurAbsenceService;
    private final FiliereService filiereService;
    private final NiveauService niveauService;
    private final ElementService elementService;
    private final TypeSeanceService typeSeanceService;
    private final EnseignantService enseignantService;
    private final EtudiantService etudiantService ;


    @GetMapping("/etudiants")
    public String showForm(
            Model model,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size){
        model.addAttribute("niveaux", niveauService.getAll(page, size));
        return "absences/administrateur/absencesSearchForm";
    }

    @PostMapping("/etudiants")
    public String showSearchAbsencesResults(
            @RequestParam(name = "niveauId")Long id,
            Model model
            ) throws NotFoundException {


        model.addAttribute("niveau",niveauService.getNiveauById(id));
        model.addAttribute("etudiants", administrateurAbsenceService.
                getEtudiantsByNiveau(niveauService.getNiveauById(id)));


        return "absences/administrateur/etudiantsList";

    }

    @PostMapping("/etudiants/absences")
    public String showEtudiantAbsences(
            @RequestParam(name = "selectedEtudiant") Long id,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "keyword", defaultValue = "") String keyword,
            Model model
    ) throws NotFoundException {

        Page<AbsenceDTO> absencesPage =  administrateurAbsenceService.getEtudiantAbsences(
                etudiantService.getById(id),
                keyword, page, size);


        model.addAttribute("etudiantAbsences",absencesPage);

        model.addAttribute("pages", new int[absencesPage.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        model.addAttribute("max", absencesPage.getTotalPages() - 1);
        model.addAttribute("size", size);



        return "absences/administrateur/listeAbsences";
    }

    //TODO:
    // modfiier une absence


    @GetMapping("/addAbsencesForm")
    public String showAddAbsenceForm(Model model){

        model.addAttribute("elements",elementService.getAllElements());
        model.addAttribute("enseignants",enseignantService.getAll());
        model.addAttribute("typeSeances", typeSeanceService.getAllTypeSeance());


        return "absences/administrateur/addAbsences";


    }

    @GetMapping ("/studentList")
    public String addAbsences(
            @RequestParam(name = "etudiants") String etudiantsCNE,
            Model model
    ) throws NotFoundException {

        model.addAttribute("etudiantsList",
                administrateurAbsenceService.showProvidedEtudiantList(etudiantsCNE));

        return "absences/administrateur/providedEtudiantList";

    }


    @PostMapping("/addAbsences")
    public String addAbsences(
            @RequestParam(name = "absenceStart") String absenceStart,
            @RequestParam(name = "absenceEnd") String absenceEnd,
            @RequestParam(name = "selectedElement") Long elementId,
            @RequestParam(name = "selectedEnseignant") Long enseignantId,
            @RequestParam(name = "etudiants") String etudiantsCNE,
            @RequestParam(name = "typeSeanceSelected") Long typeSeanceId,
            Model model
    ) throws NotFoundException, ParseException {


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        model.addAttribute("absencesList",
                administrateurAbsenceService.createAbsence(
                        elementId,
                        enseignantId,
                        simpleDateFormat.parse(absenceStart),
                        simpleDateFormat.parse(absenceEnd),
                        etudiantsCNE,
                        typeSeanceId));
        return "redirect:/administrateur/absences/addAbsencesFrom";


    }




}
