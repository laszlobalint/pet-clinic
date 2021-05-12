package hu.laszlobalint.spring.petclinic.controller;

import hu.laszlobalint.spring.petclinic.service.VetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/vets")
public class VetController {

    private final VetService vetService;

    public VetController(VetService vetService) {
        this.vetService = vetService;
    }

    @GetMapping({"", "/", "/index", "/index.html", "/vets.html"})
    public ModelAndView list() {

        return new ModelAndView("vets/index", new ModelMap("vets", vetService.findAll()));
    }
}
