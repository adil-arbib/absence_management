package com.ensah.eservice.controllers.structurePedagogique.elements;


import com.ensah.eservice.dto.elements.ElementDTO;
import com.ensah.eservice.exceptions.alreadyExists.AlreadyExistsException;
import com.ensah.eservice.exceptions.notfound.NotFoundException;
import com.ensah.eservice.services.structure_pedagogique.ElementService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/administrateur/elements")
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
        return "redirect:/administrateur/elements/create";
    }

    @GetMapping
    public String allElements(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "keyword", defaultValue = "") String keyword,
            Model model) {

        Page<ElementDTO> elementsPage = elementService.getElementsPage(page, size, keyword);
        model.addAttribute("elementsPage", elementsPage);
        model.addAttribute("pages", new int[elementsPage.getTotalPages()]);
        model.addAttribute("currentPage" ,page);
        model.addAttribute("keyword", keyword);
        model.addAttribute("max", elementsPage.getTotalPages() -1 );
        model.addAttribute("size" ,size);
        return "elements/all";
    }


    @PostMapping("/delete")
    public String deleteElement(@RequestParam("id") Long id) throws NotFoundException {
        elementService.deleteElement(id);
        return "redirect:/administrateur/elements";
    }

    @GetMapping("/{id}")
    public String showElementPage(@PathVariable Long id, Model model) throws NotFoundException {
        model.addAttribute("element", elementService.getElementById(id));
        return "elements/element";
    }

    @PostMapping("/update")
    public String updateElement(@ModelAttribute("element") ElementDTO elementDTO) throws NotFoundException {
        elementService.updateElement(elementDTO);
        return "redirect:/administrateur/elements";
    }

}
