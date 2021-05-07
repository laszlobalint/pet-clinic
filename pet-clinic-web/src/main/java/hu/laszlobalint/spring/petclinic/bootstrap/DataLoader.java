package hu.laszlobalint.spring.petclinic.bootstrap;

import hu.laszlobalint.spring.petclinic.model.*;
import hu.laszlobalint.spring.petclinic.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final PetService petService;
    private final PetTypeService petTypeService;
    private final SpecialityService specialityService;
    private final VetService vetService;
    private final VisitService visitService;

    public DataLoader(OwnerService ownerService, PetService petService, PetTypeService petTypeService, SpecialityService specialityService, VetService vetService, VisitService visitService) {
        this.ownerService = ownerService;
        this.petService = petService;
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
        System.out.println("\n>>> BOOTSTRAP DATA --> STARTED <<<\n");

        PetType dog = new PetType("Dog");
        PetType cat = new PetType("Cat");

        Speciality radiology = new Speciality("Radiology");
        Speciality surgery = new Speciality("Surgery");
        Speciality dentistry = new Speciality("Dentistry");

        Owner owner1 = new Owner("Michael", "Weston", "123 Brickerel", "Miami", "1231231234", null);
        Pet mikeDog = new Pet("Rosco", dog, owner1, LocalDate.now(), null);
        owner1.getPets().add(mikeDog);

        Owner owner2 = new Owner("Fiona", "Glenanne", "123 Brickerel", "Miami", "1231231234", null);
        Pet fionaCat = new Pet("Just Cat", dog, owner2, LocalDate.now(), null);
        owner2.getPets().add(fionaCat);

        Visit catVisit = new Visit(LocalDate.now(), "Fiver and headache", fionaCat);

        Vet vet1 = new Vet("Sam", "Axe");
        vet1.getSpecialities().add(radiology);
        vet1.getSpecialities().add(surgery);

        Vet vet2 = new Vet("Jessie", "Porter");
        vet2.getSpecialities().add(radiology);
        vet2.getSpecialities().add(dentistry);

        petTypeService.save(dog);
        petTypeService.save(cat);
        specialityService.save(radiology);
        specialityService.save(surgery);
        specialityService.save(dentistry);
        ownerService.save(owner1);
        ownerService.save(owner2);
        petService.save(fionaCat);
        petService.save(mikeDog);
        visitService.save(catVisit);
        vetService.save(vet1);
        vetService.save(vet2);

        System.out.println("\n>>> BOOTSTRAP DATA --> FINISHED <<<\n");
    }
}
