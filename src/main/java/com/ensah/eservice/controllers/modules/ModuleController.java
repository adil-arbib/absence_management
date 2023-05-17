package com.ensah.eservice.controllers.modules;


import com.ensah.eservice.dto.elements.ElementDTO;
import com.ensah.eservice.dto.elements.ElementMapper;
import com.ensah.eservice.dto.modules.ModuleDTO;
import com.ensah.eservice.exceptions.alreadyExists.AlreadyExistsException;
import com.ensah.eservice.repositories.ElementRepository;
import com.ensah.eservice.services.ModuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/modules")
@RequiredArgsConstructor
public class ModuleController {

    private final ModuleService moduleService;

    private final ElementRepository elementRepository;

    private final ElementMapper elementMapper;

    @GetMapping("/create")
    public String showPage(Model model) {
        model.addAttribute("listElements",
                elementMapper.toElementDTOList(elementRepository.findAll()));

        model.addAttribute("moduleDTO", new ModuleDTO());
        return "modules/create";
    }

    @PostMapping("/create")
    public String addModule(@ModelAttribute("moduleDTO") ModuleDTO moduleDTO, Model model){

        try {
            moduleService.create(moduleDTO);

        }catch (AlreadyExistsException e){
            model.addAttribute("toastMessage ",e.getMessage());
        }
        return "modules/create";
    }

    @GetMapping
    public String allModules(Model model){
        model.addAttribute("modules",moduleService.getALl());
        return "modules/all";
    }

}
