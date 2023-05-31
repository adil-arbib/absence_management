package com.ensah.eservice.controllers.niveau;


import com.ensah.eservice.dto.niveau.NiveauDTO;
import com.ensah.eservice.exceptions.alreadyExists.AlreadyExistsException;
import com.ensah.eservice.exceptions.notfound.NotFoundException;
import com.ensah.eservice.models.Niveau;
import com.ensah.eservice.services.NiveauService;
import com.ensah.eservice.services.structure_pedagogique.ModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/niveaux")
public class NiveauController {

    private final NiveauService niveauService;
    private final ModuleService moduleService;


    @GetMapping
    public String showAll(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "keyword", defaultValue = "") String keyword,
            Model model){

        Page<NiveauDTO>  niveauPage = niveauService.getNiveauPage(page, size, keyword);

        model.addAttribute("niveauPage", niveauPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("pages", new int[niveauPage.getTotalPages()]);
        model.addAttribute("keyword", keyword);
        model.addAttribute("max", niveauPage.getTotalPages() - 1);
        model.addAttribute("size", size);



        return "niveaux/all";
    }

    @GetMapping("/create")
    public String showCreatePage(Model model){
        model.addAttribute("modules", moduleService.getAll());
        model.addAttribute("niveauDTO", new NiveauDTO());

        return "niveaux/create";
    }

    @PostMapping("/create")
    public String addNiveau(
            @ModelAttribute NiveauDTO niveauDTO,
            @RequestParam(name = "selectedModules", required = false)List<Long> modulesIds) throws AlreadyExistsException, NotFoundException {

        niveauService.create(niveauDTO, modulesIds);

        return "redirect:/niveaux/create";
    }

    @GetMapping("/{id}")
    public String showNiveauPage(Model model, @PathVariable Long id) throws NotFoundException {

        model.addAttribute("niveau", niveauService.getNiveauById(id));
        model.addAttribute("niveauRestModules", niveauService.niveauOtherModules(id));

        return "niveaux/niveau";
    }

    @PostMapping("/update")
    public String updateNiveau(@ModelAttribute("niveau")NiveauDTO niveauDTO) throws NotFoundException {
        System.out.println(niveauDTO);
        niveauService.update(niveauDTO);

        return "redirect:/niveaux/" + niveauDTO.getId();
    }

    @PostMapping("/delete")
    public  String removeNiveau(@RequestParam("id") Long id) throws NotFoundException {
        niveauService.removeNiveau(id);

        return "redirect:/niveaux";

    }


    @PostMapping("/{niveauId}/modules/add")
    public String addModuleToNiveau(
            @PathVariable Long niveauId,
            @RequestParam("selectedModules") List<Long> modulesIds ) throws NotFoundException {

        niveauService.addModulesToNiveau(niveauId, modulesIds);

        return "redirect:/niveaux/"+niveauId;

    }

    @PostMapping("/{niveauId}/modules/remove")
    public String RemoveModuleFromNiveau(
            @PathVariable Long niveauId,
            @RequestParam(name = "moduleId") Long moduleId) throws NotFoundException {

        niveauService.removeModuleFromNiveau(niveauId, moduleId);

        return "redirect:/niveaux/" + niveauId;
    }

}
