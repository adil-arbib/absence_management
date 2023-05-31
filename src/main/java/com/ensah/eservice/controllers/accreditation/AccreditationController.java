package com.ensah.eservice.controllers.accreditation;

import com.ensah.eservice.dto.accreditations.AccreditationDTO;
import com.ensah.eservice.exceptions.notfound.NotFoundException;
import com.ensah.eservice.services.AccreditationService;
import com.ensah.eservice.services.FiliereService;
import com.ensah.eservice.services.members.EnseignantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.NotActiveException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/accreditations")
public class AccreditationController {


    private final AccreditationService accreditationService;
    private final EnseignantService enseignantService;

    private final FiliereService filiereService;


    @GetMapping("/create")
    public String showForm(Model model) throws NotFoundException {

        model.addAttribute("accreditationDTO", new AccreditationDTO());
        model.addAttribute("enseignantList", filiereService.getFreeEnseignants());

        return "accreditations/create";
    }

    @PostMapping("/create")
    public String createAccreditation(
            @ModelAttribute("accreditationDTO") AccreditationDTO accreditationDTO,
            @RequestParam("selectedEnseignant") Long id
            ) throws NotActiveException {

            accreditationService.update(accreditationDTO, id);


        return "redirect:/accreditations/create";

    }


    @GetMapping("/{id}")
    public String showAccreditation(@PathVariable Long id, Model model) throws NotFoundException {

        model.addAttribute("accreditationDTO",accreditationService.findById(id));
        model.addAttribute("enseignantList", filiereService.getFreeEnseignants());
        System.out.println(accreditationService.findById(id));

        return "accreditations/accreditation";

    }

    @PostMapping("/update")
    public String updateAccreditation(
            @ModelAttribute("accreditationDTO")AccreditationDTO accreditationDTO,
            @RequestParam("selectedEnseignant") Long id
            ) throws NotActiveException {

        accreditationService.update(accreditationDTO, id);
        System.out.println(accreditationDTO);
        return "redirect:/accreditations/" +  accreditationDTO.getId();

    }
}
