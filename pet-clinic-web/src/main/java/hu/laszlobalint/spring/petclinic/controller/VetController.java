package hu.laszlobalint.spring.petclinic.controller;

import hu.laszlobalint.spring.petclinic.service.VetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vets")
public class VetController {

    private final VetService vetService;

    public VetController(VetService vetService) {
        this.vetService = vetService;
    }

    @GetMapping({"", "/", "/index", "/index.html", "/vets.html"})
    public String list(Model model) {
        model.addAttribute("vets", vetService.findAll());

        return "vets/index";
    }
}
