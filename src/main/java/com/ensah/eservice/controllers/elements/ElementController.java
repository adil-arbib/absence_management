package com.ensah.eservice.controllers.elements;


import com.ensah.eservice.dto.elements.ElementDTO;
import com.ensah.eservice.exceptions.alreadyExists.AlreadyExistsException;
import com.ensah.eservice.services.ElementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Array;
import java.util.Arrays;

@Controller
@RequestMapping("/elements")
@RequiredArgsConstructor
public class ElementController {

    private final ElementService elementService;

    @GetMapping("/create")
    public String showPage(Model model) {
        model.addAttribute("elementDTO", new ElementDTO());
        return "elements/create";
    }


    @PostMapping("/create")
    public String addElement(@ModelAttribute("elementDTO") ElementDTO elementDTO, Model model) {
        try {
            elementService.create(elementDTO);
        } catch (AlreadyExistsException e) {
            model.addAttribute("toastMessage", e.getMessage());
        }
        return "elements/create";
    }

    @GetMapping
    public String allElements(Model model) {
        model.addAttribute("elements", elementService.getAll());
        return "elements/all";
    }


}
