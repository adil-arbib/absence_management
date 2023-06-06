package com.ensah.eservice.controllers.absences.administrateur;


import com.ensah.eservice.dto.absence.AbsenceDTO;
import com.ensah.eservice.dto.users.etudiant.EtudiantDTO;
import com.ensah.eservice.exceptions.notfound.NotFoundException;
import com.ensah.eservice.models.Absence;
import com.ensah.eservice.models.AbsenceEtat;
import com.ensah.eservice.repositories.InscriptionRepository;
import com.ensah.eservice.services.FiliereService;
import com.ensah.eservice.services.InscriptionService;
import com.ensah.eservice.services.NiveauService;
import com.ensah.eservice.services.TypeSeanceService;
import com.ensah.eservice.services.absences.AbsenceService;
import com.ensah.eservice.services.absences.administrateur.AdministrateurAbsenceService;
import com.ensah.eservice.services.members.EnseignantService;
import com.ensah.eservice.services.members.EtudiantService;
import com.ensah.eservice.services.structure_pedagogique.ElementService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequiredArgsConstructor
@RequestMapping("/administrateur/absences")
public class AdministrateurController {

    private final AdministrateurAbsenceService administrateurAbsenceService;
    private final NiveauService niveauService;
    private final ElementService elementService;
    private final TypeSeanceService typeSeanceService;
    private final EnseignantService enseignantService;
    private final AbsenceService absenceService;
    private final InscriptionService inscriptionService;
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


    @GetMapping("/etudiants/{id}/absences")
    public String showEtudiantAbsences(
            @PathVariable Long id,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "keyword", defaultValue = "") String keyword,
            Model model) throws NotFoundException {
        Page<AbsenceDTO> absencesPage =  administrateurAbsenceService.getEtudiantAbsences(
                etudiantService.getById(id),
                keyword, page, size);
        model.addAttribute("etudiantAbsences",absencesPage);

        model.addAttribute("currentEtudiant",etudiantService.getById(id));

        model.addAttribute("pages", new int[absencesPage.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        model.addAttribute("max", absencesPage.getTotalPages() - 1);
        model.addAttribute("size", size);


    return "absences/administrateur/listeAbsences";

    }

//    @PostMapping("/etudiants/absences/{id}")
//    public String showEtudiantAbsences(
//            @PathVariable(name = "selectedEtudiant") Long id,
//
//            Model model
//    ) throws NotFoundException {
//        System.out.println(etudiantService.getById(id));
//
//
//
//
//        model.addAttribute("etudiantAbsences",absencesPage);
//
//        model.addAttribute("currentEtudiant",etudiantService.getById(id));
//
//        model.addAttribute("pages", new int[absencesPage.getTotalPages()]);
//        model.addAttribute("currentPage", page);
//        model.addAttribute("keyword", keyword);
//        model.addAttribute("max", absencesPage.getTotalPages() - 1);
//        model.addAttribute("size", size);
//
//
//
//        return "redirect:absences/administrateur/listeAbsences";
//    }

    @GetMapping("/etudiants/absences/{id}")
    public String showAbsenceToBeUpdated(@PathVariable Long id, Model model) throws NotFoundException {


        model.addAttribute("currentAbsence",absenceService.getById(id));
        model.addAttribute("elements",elementService.getAllElements());
        model.addAttribute("enseignants",enseignantService.getAll());
        model.addAttribute("typeSeances",typeSeanceService.getAllTypeSeance());


        return "absences/administrateur/absence";

    }

    @PostMapping("/etudiants/absences/update")
    public String updateAbsence(
            @ModelAttribute("currentAbsence") AbsenceDTO absenceDTO,
            @RequestParam(name = "selectedElement") Long elementId,
            @RequestParam(name = "selectedEnseignant") Long enseignantId,
            @RequestParam(name = "typeSeanceSelected") Long typeSeanceId,
            @RequestParam(name = "etudiant") String etudiantCNE,
            @RequestParam(name = "absenceStart") String absenceStart,
            @RequestParam(name = "absenceEnd") String absenceEnd,
            @RequestParam(name = "etat") String etat
          ) throws NotFoundException, ParseException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");


        absenceDTO.setElement(elementService.getElementById(elementId));
        absenceDTO.setEnseignant(enseignantService.getById(enseignantId));
        absenceDTO.setTypeSeance(typeSeanceService.getTypeSeance(typeSeanceId));
        absenceDTO.setInscription(inscriptionService.getByEtudiant(etudiantCNE));
        absenceDTO.setAbsenceStart(simpleDateFormat.parse(absenceStart));
        absenceDTO.setAbsenceEnd(simpleDateFormat.parse(absenceEnd));

        AbsenceEtat etatAbsence;


      switch (etat){
          case "JUSTIFIEE":
              etatAbsence = AbsenceEtat.JUSTIFIEE;
              break;
          case "ANNULEE":
              etatAbsence = AbsenceEtat.ANNULEE;
              break;
          default:
              // l etat par defaut c est non justifie
              etatAbsence = AbsenceEtat.NON_JUSTIFIEE;
              break;
      }

        absenceDTO.setEtat(etatAbsence);

        administrateurAbsenceService.updateAbsence(absenceDTO);


        return "redirect:/administrateur/absences/etudiants/absences/"+ absenceDTO.getId();

    }

//    @PostMapping("/etudiants/absences/{id}/pieceJustificative")
//    public String attachPJ(
//            @PathVariable Long id,
//            @RequestParam MultipartFile pj
//           ) throws NotFoundException, IOException {
//
//        administrateurAbsenceService.attachPieceJustificative(id, pj);
//
//        return "redirect:/administrateur/absences/etudiants/absences/"+ id;
//    }


    @GetMapping("/addAbsencesForm")
    public String showAddAbsenceForm(Model model){

        model.addAttribute("elements",elementService.getAllElements());
        model.addAttribute("enseignants",enseignantService.getAll());
        model.addAttribute("typeSeances", typeSeanceService.getAllTypeSeance());


        return "absences/administrateur/addAbsences";


    }

    @GetMapping ("/studentList")
    public String showCneStudentList(
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
        return "redirect:/administrateur/absences/addAbsencesForm";


    }

    @PostMapping("/etudiants/absences/{id}/delete")
    public String deleteAbsence(
            @PathVariable Long id,
            @RequestParam("selectedEtudiant") Long etudiantId
           ) throws NotFoundException {
            administrateurAbsenceService.deleteAbsence(id);

            return "redirect:/administrateur/absences/etudiants/"+etudiantId+"/absences";
    }







}
