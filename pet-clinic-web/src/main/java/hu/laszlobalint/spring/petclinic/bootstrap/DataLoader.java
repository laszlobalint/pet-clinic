package hu.laszlobalint.spring.petclinic.bootstrap;

import hu.laszlobalint.spring.petclinic.model.*;
import hu.laszlobalint.spring.petclinic.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final PetTypeService petTypeService;
    private final SpecialityService specialityService;
    private final VetService vetService;
    private final VisitService visitService;

    public DataLoader(OwnerService ownerService, PetTypeService petTypeService, SpecialityService specialityService, VetService vetService, VisitService visitService) {
        this.ownerService = ownerService;
        this.petTypeService = petTypeService;
        this.specialityService = specialityService;
        this.vetService = vetService;
        this.visitService = visitService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (petTypeService.findAll().size() == 0)
            loadMappedData();
    }

    private void loadMappedData() {
        System.out.println("\n>>> BOOTSTRAP DATA <<<\n");

        PetType dog = new PetType("Dog");
        petTypeService.save(dog);
        PetType cat = new PetType("Cat");
        petTypeService.save(cat);

        Speciality radiology = new Speciality("Radiology");
        Speciality surgery = new Speciality("Surgery");
        Speciality dentistry = new Speciality("Dentistry");
        specialityService.save(radiology);
        specialityService.save(surgery);
        specialityService.save(dentistry);

        Owner owner1 = new Owner("Michael", "Weston", "123 Brickerel", "Miami", "1231231234", null);
        Pet mikeDog = new Pet("Rosco", dog, owner1, LocalDate.now(), null);
        owner1.getPets().add(mikeDog);
        ownerService.save(owner1);

        Owner owner2 = new Owner("Fiona", "Glenanne", "123 Brickerel", "Miami", "1231231234", null);
        Pet fionaCat = new Pet("Just Cat", dog, owner2, LocalDate.now(), null);
        owner2.getPets().add(fionaCat);
        ownerService.save(owner2);

        Visit catVisit = new Visit(LocalDate.now(), "Fiver and headache", fionaCat);
        visitService.save(catVisit);

        Vet vet1 = new Vet("Sam", "Axe");
        vetService.save(vet1);
        vet1.getSpecialities().add(radiology);
        vet1.getSpecialities().add(surgery);

        Vet vet2 = new Vet("Jessie", "Porter");
        vetService.save(vet2);
        vet2.getSpecialities().add(radiology);
        vet2.getSpecialities().add(dentistry);
    }
}
