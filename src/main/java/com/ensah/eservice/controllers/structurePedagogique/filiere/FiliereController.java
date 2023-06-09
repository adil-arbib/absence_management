package com.ensah.eservice.controllers.structurePedagogique.filiere;


import com.ensah.eservice.dto.filieres.FiliereDTO;
import com.ensah.eservice.dto.filieres.FiliereMapper;
import com.ensah.eservice.exceptions.alreadyExists.AlreadyExistsException;
import com.ensah.eservice.exceptions.notfound.NotFoundException;
import com.ensah.eservice.services.AccreditationService;
import com.ensah.eservice.services.FiliereService;
import com.ensah.eservice.services.NiveauService;
import com.ensah.eservice.services.members.EnseignantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/administrateur/filieres")
public class FiliereController {

    private final FiliereService filiereService;

    @GetMapping
    public String showAll(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "keyword", defaultValue = "") String keyword,
            Model model){

        Page<FiliereDTO> filierePage = filiereService.getFilierePage(page, size, keyword);

        model.addAttribute("filierePage", filierePage);
        model.addAttribute("currentPage", page);
        model.addAttribute("pages", new int[filierePage.getTotalPages()]);
        model.addAttribute("keyword", keyword);
        model.addAttribute("max", filierePage.getTotalPages() - 1);
        model.addAttribute("size", size);


        return "filieres/all";
    }

    @GetMapping("/create")
    public String showCreatePage(Model model) throws NotFoundException {
        model.addAttribute("niveaux", filiereService.getRestOfNiveaux());
        model.addAttribute("enseignants", filiereService.getFreeEnseignants());
        System.out.println(filiereService.getFreeEnseignants());
        System.out.println(new Date());
        model.addAttribute("filiereDTO", new FiliereDTO());


        return "filieres/create";
    }

    @PostMapping("/create")
    public String addFiliere(
            @ModelAttribute FiliereDTO filiereDTO,
            @RequestParam(name = "selectedNiveaux", required = false)List<Long> niveauxIds,
            @RequestParam(name = "selectedEnseignant", required = false)Long enseingnatId
    ) throws AlreadyExistsException, NotFoundException {

        filiereService.create(filiereDTO, enseingnatId, niveauxIds);

        return "redirect:/administrateur/filieres/create";
    }

    @GetMapping("/{id}")
    public String showFilierePage(Model model, @PathVariable Long id) throws NotFoundException {

        model.addAttribute("filiere", filiereService.getFiliereById(id));
        model.addAttribute("currentCoordinateur", filiereService.getCurrentCoordinnateur(id));
        model.addAttribute("restOfNiveaux", filiereService.getRestOfNiveaux());
        model.addAttribute("restOfEnseignants",filiereService.getFreeEnseignants());

        return "filieres/filiere";
    }

    @PostMapping("/update")
    public String updateFiliere(@ModelAttribute("filiere")FiliereDTO filiereDTO) throws NotFoundException {

                System.out.println(filiereDTO);
               filiereService.update(filiereDTO);


        return "redirect:/administrateur/filieres/" + filiereDTO.getId();
    }

    @PostMapping("/delete")
    public String removeFiliere(@RequestParam("id") Long id) throws NotFoundException {

        filiereService.deleteFiliere(id);

        return "redirect:/administrateur/filieres";
    }

    @PostMapping("/{filiereId}/enseignants/add")
    public String addCordinnateurToFiliere(
            @PathVariable Long filiereId,
            @RequestParam("selectedEnseignant") Long selectedEnseignant
    ) throws NotFoundException {
        filiereService.addCordinnateurToFiliere(filiereId,selectedEnseignant);

        return "redirect:/administrateur/filieres/" + filiereId;
    }

    @PostMapping("/{filiereId}/niveaux/add")
    public String addNiveauxToFiliere(
            @PathVariable Long filiereId,
            @RequestParam("selectedNiveaux") List<Long> selectedNiveaux
    ) throws NotFoundException {
        System.out.println(selectedNiveaux);
        filiereService.addNiveauToFiliere(filiereId,selectedNiveaux);

        return "redirect:/administrateur/filieres/" + filiereId;
    }

    @PostMapping("/{filiereId}/niveaux/remove")
    public String removeNiveauFromFiliere(
            @PathVariable Long filiereId,
            @RequestParam("niveauId") Long niveauId

    ) throws NotFoundException {
        filiereService.removeNiveauFromFilier(filiereId, niveauId);

        return "redirect:/administrateur/filieres/" + filiereId;

    }



}
