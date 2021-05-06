package hu.laszlobalint.spring.petclinic.service.jpa;

import hu.laszlobalint.spring.petclinic.model.Speciality;
import hu.laszlobalint.spring.petclinic.repository.SpecialityRepository;
import hu.laszlobalint.spring.petclinic.service.SpecialityService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("jpa")
public class SpecialityJpaService implements SpecialityService {

    private final SpecialityRepository specialityRepository;

    public SpecialityJpaService(SpecialityRepository specialityRepository) {
        this.specialityRepository = specialityRepository;
    }

    @Override
    public Set<Speciality> findAll() {
        Set<Speciality> specialties = new HashSet<>();
        specialityRepository.findAll().forEach(specialties::add);

        return specialties;
    }

    @Override
    public Speciality findById(Long id) {

        return specialityRepository.findById(id).orElse(null);
    }

    @Override
    public Speciality save(Speciality speciality) {

        return specialityRepository.save(speciality);
    }

    @Override
    public void delete(Speciality speciality) {

        specialityRepository.delete(speciality);
    }

    @Override
    public void deleteById(Long id) {

        specialityRepository.deleteById(id);
    }
}
