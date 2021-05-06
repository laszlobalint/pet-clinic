package hu.laszlobalint.spring.petclinic.service.jpa;

import hu.laszlobalint.spring.petclinic.model.PetType;
import hu.laszlobalint.spring.petclinic.repository.PetTypeRepository;
import hu.laszlobalint.spring.petclinic.service.PetTypeService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("jpa")
public class PetTypeJpaService implements PetTypeService {

    private final PetTypeRepository petTypeRepository;

    public PetTypeJpaService(PetTypeRepository petTypeRepository) {
        this.petTypeRepository = petTypeRepository;
    }

    @Override
    public Set<PetType> findAll() {
        Set<PetType> petTypes = new HashSet<>();
        petTypeRepository.findAll().forEach(petTypes::add);

        return petTypes;
    }

    @Override
    public PetType findById(Long id) {

        return petTypeRepository.findById(id).orElse(null);
    }

    @Override
    public PetType save(PetType petType) {

        return petTypeRepository.save(petType);
    }

    @Override
    public void delete(PetType petType) {

        petTypeRepository.delete(petType);
    }

    @Override
    public void deleteById(Long id) {

        petTypeRepository.deleteById(id);
    }
}
