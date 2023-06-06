package com.ensah.eservice.controllers.absences.enseignant;


import com.ensah.eservice.dto.TypeSeance.TypeSeanceDTO;
import com.ensah.eservice.dto.elements.ElementDTO;
import com.ensah.eservice.dto.niveau.NiveauDTO;
import com.ensah.eservice.dto.users.enseignant.EnseignantDTO;
import com.ensah.eservice.dto.users.enseignant.EnseignantMapper;
import com.ensah.eservice.exceptions.notfound.NotFoundException;
import com.ensah.eservice.models.Enseignant;
import com.ensah.eservice.services.FiliereService;
import com.ensah.eservice.services.InscriptionService;
import com.ensah.eservice.services.NiveauService;
import com.ensah.eservice.services.TypeSeanceService;
import com.ensah.eservice.services.absences.enseignant.EnseignantAbsenceService;
import com.ensah.eservice.services.structure_pedagogique.ElementService;
import com.ensah.eservice.utils.CurrentUser;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("enseignant/absences")
@RequiredArgsConstructor
public class EnseignantAbsenceController {

    private final NiveauService niveauService;
    private final ElementService elementService;
    private final TypeSeanceService typeSeanceService;
    private final EnseignantAbsenceService enseignantAbsenceService;
    private final EnseignantMapper enseignantMapper;

//filiere -> niveaux -> modules
    @GetMapping("/saisirAbsence")
    public String getFormAbsence(Model model){

        model.addAttribute("niveaux",niveauService.getAll() );
        model.addAttribute("matieres",elementService.getAllElements());
        model.addAttribute("types",typeSeanceService.getAllTypeSeance());
        return "absences/enseignant/AddAbsences";
    }

    @PostMapping("/search")
    public String SerachListeStudents(
                             @RequestParam("niveauId") Long niveauId,
                             @RequestParam("elementId") Long elementId,
                             @RequestParam("typeSeanceId") Long typeSeanceId,
                             Model model) {

        System.out.println(niveauId);
        System.out.println(elementId);
        System.out.println(typeSeanceId);
        try {
            NiveauDTO niveauDTO = niveauService.getNiveauById(niveauId);
            ElementDTO elementDTO= elementService.getElementById(elementId);
            TypeSeanceDTO typeSeanceDTO=typeSeanceService.getTypeSeance(typeSeanceId);
            model.addAttribute("element",elementDTO);
            model.addAttribute("typeSeance",typeSeanceDTO);
            model.addAttribute("found", true);
            model.addAttribute("etudiants", enseignantAbsenceService.getEtudiantsByNiveau(niveauDTO));
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }

        return "absences/enseignant/AddAbsences";
    }


    @PostMapping("/AddAbsence")
    public String addAbsences(
            @RequestParam(name="IdSelected") List<Long> IdSelected,
            @RequestParam(name = "absenceStart") String absenceStart,
            @RequestParam(name = "absenceEnd") String absenceEnd,
            @RequestParam(name = "element") Long element,
            @RequestParam(name = "typeSeance") Long typeSeance
    ) throws NotFoundException, ParseException {

        ElementDTO elementDTO= elementService.getElementById(element);
        TypeSeanceDTO typeSeanceDTO=typeSeanceService.getTypeSeance(typeSeance);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
       enseignantAbsenceService.createAbsence(elementDTO,
               simpleDateFormat.parse(absenceStart),simpleDateFormat.parse(absenceEnd),IdSelected,typeSeanceDTO);
        return "absences/enseignant/AddAbsences";
    }


    @GetMapping("/all")
    public String showAll(Model model){
        model.addAttribute("listeAbsence",enseignantAbsenceService.getAll());
        return "absences/enseignant/All";
    }

}
