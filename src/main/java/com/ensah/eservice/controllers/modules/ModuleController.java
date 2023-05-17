package com.ensah.eservice.controllers.modules;


import com.ensah.eservice.dto.elements.ElementDTO;
import com.ensah.eservice.dto.elements.ElementMapper;
import com.ensah.eservice.dto.modules.ModuleDTO;
import com.ensah.eservice.exceptions.alreadyExists.AlreadyExistsException;
import com.ensah.eservice.exceptions.notfound.NotFoundException;
import com.ensah.eservice.repositories.ElementRepository;
import com.ensah.eservice.services.ElementService;
import com.ensah.eservice.services.ModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/modules")
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
        return "redirect:/modules/create";
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

}
