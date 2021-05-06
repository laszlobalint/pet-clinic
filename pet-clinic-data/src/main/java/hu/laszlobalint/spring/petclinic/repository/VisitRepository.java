package hu.laszlobalint.spring.petclinic.repository;

import hu.laszlobalint.spring.petclinic.model.Visit;
import org.springframework.data.repository.CrudRepository;

public interface VisitRepository extends CrudRepository<Visit, Long> {
}
