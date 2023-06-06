package com.ensah.eservice.controllers.structurePedagogique.modules;


import com.ensah.eservice.dto.modules.ModuleDTO;
import com.ensah.eservice.exceptions.alreadyExists.AlreadyExistsException;
import com.ensah.eservice.exceptions.notfound.NotFoundException;
import com.ensah.eservice.services.structure_pedagogique.ElementService;
import com.ensah.eservice.services.structure_pedagogique.ModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/administrateur/modules")
@RequiredArgsConstructor
public class ModuleController {

    private final ModuleService moduleService;
    private final ElementService elementService;


    @GetMapping("/create")
    public String showPage(Model model) {
        model.addAttribute("elements",elementService.getAllElements());
        model.addAttribute("moduleDTO", new ModuleDTO());
        return "modules/create";
    }

    @PostMapping("/create")
    public String addModule(
            @ModelAttribute("moduleDTO") ModuleDTO moduleDTO,
            @RequestParam(name = "selectedElements", required = false) List<Long> selectedElementsIds
    ) throws AlreadyExistsException, NotFoundException {
        moduleService.create(moduleDTO, selectedElementsIds);
        return "redirect:/administrateur/modules/create";
    }

    @GetMapping
    public String allModules(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "keyword", defaultValue = "") String keyword,
            Model model) {
        Page<ModuleDTO> modulesPage = moduleService.getModulesPage(page, size, keyword);
        model.addAttribute("modulesPage", modulesPage);
        model.addAttribute("pages", new int[modulesPage.getTotalPages()]);
        model.addAttribute("currentPage" ,page);
        model.addAttribute("keyword", keyword);
        model.addAttribute("max", modulesPage.getTotalPages() -1 );
        model.addAttribute("size" ,size);
        return "modules/all";
    }

    @GetMapping("/{id}")
    public String showModulePage(@PathVariable Long id, Model model) throws NotFoundException {
        model.addAttribute("module", moduleService.getModuleById(id));
        model.addAttribute("restElements", moduleService.getRestElements(id));

        return "modules/module";
    }

    @PostMapping("/update")
    public String updateModule(@ModelAttribute("module") ModuleDTO moduleDTO) throws NotFoundException {
        moduleService.update(moduleDTO);
        System.out.println(moduleDTO);
        return "redirect:/administrateur/modules/"+moduleDTO.getId();
    }



    @PostMapping("/{moduleId}/elements/remove")
    public String removeElementFromModule(
            @PathVariable("moduleId") Long moduleId,
            @RequestParam("elementId") Long elementId
    ) throws NotFoundException {
        moduleService.removeElementFromModule(moduleId, elementId);
        return "redirect:/administrateur/modules/"+moduleId;
    }

    @PostMapping("/{moduleId}/elements/add")
    public String addElementsToModule(
            @PathVariable("moduleId") Long moduleId,
            @RequestParam(name = "selectedElements") List<Long> selectedElementsIds
    ) throws NotFoundException {
        moduleService.addElementToModule(moduleId, selectedElementsIds);
        return "redirect:/administrateur/modules/"+moduleId;
    }

    @PostMapping("/delete")
    public String deleteElement(@RequestParam("id") Long id) throws NotFoundException {
        moduleService.deleteModule(id);
        return "redirect:/administrateur/modules";
    }



}
