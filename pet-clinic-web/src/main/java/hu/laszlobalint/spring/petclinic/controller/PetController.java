package hu.laszlobalint.spring.petclinic.controller;

import hu.laszlobalint.spring.petclinic.model.Owner;
import hu.laszlobalint.spring.petclinic.model.Pet;
import hu.laszlobalint.spring.petclinic.model.PetType;
import hu.laszlobalint.spring.petclinic.service.OwnerService;
import hu.laszlobalint.spring.petclinic.service.PetService;
import hu.laszlobalint.spring.petclinic.service.PetTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Collection;

@Controller
@RequestMapping("/owners/{ownerId}")
public class PetController {

    private final OwnerService ownerService;
    private final PetService petService;
    private final PetTypeService petTypeService;

    public PetController(OwnerService ownerService, PetService petService, PetTypeService petTypeService) {
        this.ownerService = ownerService;
        this.petService = petService;
        this.petTypeService = petTypeService;
    }

    @InitBinder("owner")
    public void setAllowedFields(WebDataBinder webDataBinder) {
        webDataBinder.setDisallowedFields("id");
    }

    @ModelAttribute("pet_types")
    public Collection<PetType> getPetTypes() {

        return petTypeService.findAll();
    }

    @ModelAttribute("owner")
    public Owner findOwner(@PathVariable Long ownerId) {

        return ownerService.findById(ownerId);
    }

    @GetMapping("/pets/new")
    public String showCreateForm(Owner owner, Model model) {
        Pet pet = new Pet();
        owner.getPets().add(pet);
        pet.setOwner(owner);
        model.addAttribute("pet", pet);

        return "pets/create-or-update-form";
    }

    @PostMapping("/pets/new")
    public String processCreateForm(@Valid Pet pet, BindingResult bindingResult, Owner owner, Model model) {
        if (StringUtils.hasLength(pet.getName()) && pet.isNew() && owner.getPet(pet.getName(), true) != null)
            bindingResult.rejectValue("name", "duplicate", "already exists");

        owner.getPets().add(pet);

        if (bindingResult.hasErrors()) {
            model.addAttribute("pet", pet);

            return "pets/create-or-update-form";

        } else {
            pet.setOwner(owner);
            petService.save(pet);
            ownerService.save(owner);

            return String.format("redirect:/owners/%s", owner.getId());
        }
    }

    @GetMapping("/pets/{petId}/edit")
    public ModelAndView showUpdateForm(@PathVariable Long petId) {

        return new ModelAndView("pets/create-or-update-form", new ModelMap("pet", petService.findById(petId)));
    }

    @PostMapping("/pets/{petId}/edit")
    public String processUpdateForm(@Valid Pet pet, BindingResult bindingResult, Owner owner, Model model) {
        if (bindingResult.hasErrors()) {
            pet.setOwner(owner);
            model.addAttribute("pet", pet);

            return "pets/create-or-update-form";

        } else {
            owner.getPets().add(pet);
            petService.save(pet);

            return String.format("redirect:/owners/%s", owner.getId());
        }
    }
}