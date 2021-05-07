package hu.laszlobalint.spring.petclinic.controller;

import hu.laszlobalint.spring.petclinic.service.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/owners")
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder webDataBinder) {
        webDataBinder.setDisallowedFields("id");
    }

    @GetMapping({"", "/", "/index", "/index.html"})
    public ModelAndView listAll() {

        return new ModelAndView("owners/index", new ModelMap("owners", ownerService.findAll()));
    }

    @GetMapping("/{id}")
    public ModelAndView showById(@PathVariable("id") Long id) {

        return new ModelAndView("owners/owner-details", new ModelMap("owner", ownerService.findById(id)));
    }

    @GetMapping("/find")
    public String find() {

        return "error/not-implemented";
    }
}
