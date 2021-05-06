package hu.laszlobalint.spring.petclinic.repository;

import hu.laszlobalint.spring.petclinic.model.Pet;
import org.springframework.data.repository.CrudRepository;

public interface PetRepository extends CrudRepository<Pet, Long> {
}
