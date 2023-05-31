package com.ensah.eservice.controllers.filiere;


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
@RequestMapping("/filieres")
public class FiliereController {

    private final FiliereService filiereService;
    private final NiveauService niveauService;
    private final AccreditationService accreditationService;
    private final EnseignantService enseignantService;

    private final FiliereMapper filiereMapper;


    @GetMapping
    public String showAll(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "keyword", defaultValue = "") String keyword,
            Model model){

        Page<FiliereDTO> filierePage = filiereService.getFilierePage(page, size, keyword);
        System.out.println(filierePage);

        model.addAttribute("filierePage", filierePage);
        model.addAttribute("currentPage", page);
        model.addAttribute("pages", new int[filierePage.getTotalPages()]);
        model.addAttribute("keyword", keyword);
        model.addAttribute("max", filierePage.getTotalPages() - 1);
        model.addAttribute("size", size);


        return "filieres/all";
    }

    @GetMapping("/create")
    public String showCreatePage(Model model){
        model.addAttribute("niveaux", niveauService.getAll());
//        model.addAttribute("accreditations", accreditationService.getAll());
        model.addAttribute("enseignants", enseignantService.getAll());
        model.addAttribute("filiereDTO", new FiliereDTO());


//        System.out.println(accreditationService.getAll());

        return "filieres/create";
    }

    @PostMapping("/create")
    public String addFiliere(
            @ModelAttribute FiliereDTO filiereDTO,
            @RequestParam(name = "selectedNiveaux", required = false)List<Long> niveauxIds,
            @RequestParam(name = "selectedEnseignant", required = false)Long enseingnatId
    ) throws AlreadyExistsException, NotFoundException {

        Date createAt2 = filiereDTO.getCreateAt();
//        System.out.println(createAt);
//        System.out.println(createAt2);
        System.out.println(filiereDTO);
        filiereService.create(filiereDTO, enseingnatId, niveauxIds);

        return "redirect:/filieres/create";
    }

    @GetMapping("/{id}")
    public String showFilierePage(Model model, @PathVariable Long id) throws NotFoundException {

        model.addAttribute("filiere", filiereService.getFiliereById(id));
        model.addAttribute("currentCoordinateur", filiereService.getCurrentCoordinnateur(id));
        model.addAttribute("restOfNiveaux", filiereService.getRestOfNiveaux(id));
        model.addAttribute("restOfEnseignants",filiereService.getRestOfEnseignants(id));

        return "filieres/filiere";
    }

    @PostMapping("/update")
    public String updateFiliere(@ModelAttribute("filiere")FiliereDTO filiereDTO) throws NotFoundException {

                System.out.println(filiereDTO);
               filiereService.update(filiereDTO);


        return "redirect:/filieres/" + filiereDTO.getId();
    }

    @PostMapping("/delete")
    public String removeFiliere(@RequestParam("id") Long id) throws NotFoundException {

        filiereService.deleteFiliere(id);

        return "redirect:/filieres";
    }

    @PostMapping("/{filiereId}/enseignants/add")
    public String addCordinnateurToFiliere(
            @PathVariable Long filiereId,
            @RequestParam("selectedEnseignant") Long selectedEnseignant
    ) throws NotFoundException {
        filiereService.addCordinnateurToFiliere(filiereId,selectedEnseignant);

        return "redirect:/filieres/" + filiereId;
    }

    @PostMapping("/{filiereId}/niveaux/add")
    public String addNiveauxToFiliere(
            @PathVariable Long filiereId,
            @RequestParam("selectedNiveaux") List<Long> selectedNiveaux
    ) throws NotFoundException {
        System.out.println(selectedNiveaux);
        filiereService.addNiveauToFiliere(filiereId,selectedNiveaux);

        return "redirect:/filieres/" + filiereId;
    }

    @PostMapping("/{filiereId}/niveaux/remove")
    public String removeNiveauFromFiliere(
            @PathVariable Long filiereId,
            @RequestParam("niveauId") Long niveauId

    ) throws NotFoundException {
        filiereService.removeNiveauFromFilier(filiereId, niveauId);

        return "redirect:/filieres/" + filiereId;

    }



}
