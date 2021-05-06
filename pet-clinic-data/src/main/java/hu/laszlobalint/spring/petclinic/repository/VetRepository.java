package hu.laszlobalint.spring.petclinic.repository;

import hu.laszlobalint.spring.petclinic.model.Vet;
import org.springframework.data.repository.CrudRepository;

public interface VetRepository extends CrudRepository<Vet, Long> {
}
