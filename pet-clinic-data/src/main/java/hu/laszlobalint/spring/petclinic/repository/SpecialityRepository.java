package hu.laszlobalint.spring.petclinic.repository;

import hu.laszlobalint.spring.petclinic.model.Speciality;
import org.springframework.data.repository.CrudRepository;

public interface SpecialityRepository extends CrudRepository<Speciality, Long> {
}
