package hu.laszlobalint.spring.petclinic.service.jpa;

import hu.laszlobalint.spring.petclinic.model.Visit;
import hu.laszlobalint.spring.petclinic.repository.VisitRepository;
import hu.laszlobalint.spring.petclinic.service.VisitService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("jpa")
public class VisitJpaService implements VisitService {

    private final VisitRepository visitRepository;

    public VisitJpaService(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    @Override
    public Set<Visit> findAll() {
        Set<Visit> visits = new HashSet<>();
        visitRepository.findAll().forEach(visits::add);

        return visits;
    }

    @Override
    public Visit findById(Long id) {

        return visitRepository.findById(id).orElse(null);
    }

    @Override
    public Visit save(Visit visit) {

        return visitRepository.save(visit);
    }

    @Override
    public void delete(Visit visit) {

        visitRepository.delete(visit);
    }

    @Override
    public void deleteById(Long id) {

        visitRepository.deleteById(id);
    }
}
