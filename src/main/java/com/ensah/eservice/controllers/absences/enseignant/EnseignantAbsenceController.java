package com.ensah.eservice.controllers.absences.enseignant;


import com.ensah.eservice.dto.TypeSeance.TypeSeanceDTO;
import com.ensah.eservice.dto.absence.AbsenceDTO;
import com.ensah.eservice.dto.elements.ElementDTO;
import com.ensah.eservice.dto.filieres.FiliereDTO;
import com.ensah.eservice.dto.niveau.NiveauDTO;
import com.ensah.eservice.exceptions.notfound.InscriptionNotFoundException;
import com.ensah.eservice.exceptions.notfound.NotFoundException;
import com.ensah.eservice.services.FiliereService;
import com.ensah.eservice.services.InscriptionService;
import com.ensah.eservice.services.NiveauService;
import com.ensah.eservice.services.TypeSeanceService;
import com.ensah.eservice.services.absences.enseignant.EnseignantAbsenceService;
import com.ensah.eservice.services.structure_pedagogique.ElementService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping("enseignant/absences")
@RequiredArgsConstructor
public class EnseignantAbsenceController {

    private final FiliereService filiereService;
    private final NiveauService niveauService;
    private final ElementService elementService;
    private final TypeSeanceService typeSeanceService;
    private final InscriptionService inscriptionService;
    private final EnseignantAbsenceService enseignantAbsenceService;

//filiere -> niveaux -> modules
    @GetMapping("/saisirAbsence")
    public String getFormAbsence(Model model){

        model.addAttribute("filieres",filiereService.getAll());
        model.addAttribute("niveaux",niveauService.getAll() );
        model.addAttribute("modules",elementService.getAllElements());
        model.addAttribute("typeSeances",typeSeanceService.getAllTypeSeance());
        return "enseignant/AddAbsences";
    }

    @PostMapping("/saisirAbsences")
    public String addAbsence(@RequestParam("filiere") Long filiereId,
                             @RequestParam("niveau") Long niveauId,
                             @RequestParam("elementID") Long elementId,
                             @RequestParam("typeSelected") Long typeSeanceId,
                             @RequestParam("absenceStart") Date absenceStart,
                             @RequestParam("absenceEnd") Date absenceEnd,
                             Model model) throws NotFoundException {
        FiliereDTO filiereDTO= filiereService.getFiliereById(filiereId);
        NiveauDTO niveauDTO=niveauService.getNiveauById(niveauId);
        ElementDTO elementDTO= elementService.getElementById(elementId);
        TypeSeanceDTO typeSeanceDTO=typeSeanceService.getTypeSeance(typeSeanceId);

        model.addAttribute("absenceStart",absenceStart);
        model.addAttribute("absenceEnd",absenceEnd);
        model.addAttribute("filiere",filiereDTO);
        model.addAttribute("niveau",niveauDTO);
        model.addAttribute("element",elementDTO);
        model.addAttribute("typeSeance",typeSeanceDTO);

        model.addAttribute("etudiants",inscriptionService.getByNiveau(niveauDTO));
        return "enseignant/listeStudents";
    }

    @GetMapping("/All")
    public String allAbsences(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "keyword", defaultValue = "") String keyword,
            Model model) {

        Page<AbsenceDTO> absencesPage = enseignantAbsenceService.getAllAbsences(page, size, keyword);
        model.addAttribute("absencesPage", absencesPage);
        model.addAttribute("pages", new int[absencesPage.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        model.addAttribute("max", absencesPage.getTotalPages() - 1);
        model.addAttribute("size", size);
        return "enseignant/all";
    }
}
