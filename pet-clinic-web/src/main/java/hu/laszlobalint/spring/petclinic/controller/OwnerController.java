package hu.laszlobalint.spring.petclinic.controller;

import hu.laszlobalint.spring.petclinic.model.Owner;
import hu.laszlobalint.spring.petclinic.service.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

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
    public ModelAndView findOwnersForm() {

        return new ModelAndView("owners/find-owners", new ModelMap("owner", Owner.builder().build()));
    }

    @GetMapping("/searching")
    public String findOwnersProcess(Owner owner, BindingResult bindingResult, Model model) {
        if (owner.getLastName() == null)
            owner.setLastName("");

        List<Owner> result = ownerService.findAllByLastNameLike(String.format("%%%s%%", owner.getLastName()));

        if (result.isEmpty()) {
            bindingResult.rejectValue("lastName", "notFound", "not found");

            return "owners/find-owners";

        } else if (result.size() == 1) {
            owner = result.get(0);

            return String.format("redirect:/owners/%s", owner.getId());
        } else {
            model.addAttribute("result", result);

            return "owners/owners-list";
        }
    }

    @GetMapping("/new")
    public ModelAndView showCreateForm() {

        return new ModelAndView("owners/create-or-update-form", new ModelMap("owner", Owner.builder().build()));
    }

    @PostMapping("/new")
    public String processCreateForm(@Valid Owner owner, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {

            return "owners/create-or-update-form";

        } else {
            Owner savedOwner = ownerService.save(owner);

            return String.format("redirect:/owners/%s", savedOwner.getId());
        }
    }

    @GetMapping("/{id}/edit")
    public ModelAndView showUpdateForm(@PathVariable Long id) {

        return new ModelAndView("owners/create-or-update-form", new ModelMap("owner", ownerService.findById(id)));
    }

    @PostMapping("/{id}/edit")
    public String processUpdateForm(@PathVariable Long id, @Valid Owner owner, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {

            return "owners/create-or-update-form";

        } else {
            owner.setId(id);
            Owner savedOwner = ownerService.save(owner);

            return String.format("redirect:/owners/%s", savedOwner.getId());
        }
    }
}