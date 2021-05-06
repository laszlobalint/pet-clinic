package hu.laszlobalint.spring.petclinic.service.jpa;

import hu.laszlobalint.spring.petclinic.model.Owner;
import hu.laszlobalint.spring.petclinic.repository.OwnerRepository;
import hu.laszlobalint.spring.petclinic.repository.PetRepository;
import hu.laszlobalint.spring.petclinic.repository.PetTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OwnerJpaServiceTest {

    final Long OWNER_ID = 1L;
    final String LAST_NAME = "Doe";

    Owner mockOwner;

    @Mock
    OwnerRepository ownerRepository;

    @Mock
    PetRepository petRepository;

    @Mock
    PetTypeRepository petTypeRepository;

    @InjectMocks
    OwnerJpaService ownerJpaService;

    @BeforeEach
    void setUp() {
        mockOwner = Owner.builder().firstName("John").lastName("Doe").city("Boston").address("Elm street 1").telephone("1234567").pets(null).build();
        mockOwner.setId(OWNER_ID);
    }

    @Test
    void findAll() {
        Set<Owner> mockOwners = new HashSet<>();
        mockOwners.add(Owner.builder().firstName("John").lastName("Doe").city("Boston").address("Elm street 1").telephone("1234567").pets(null).build());
        mockOwners.add(Owner.builder().firstName("Jane").lastName("Doe").city("Houston").address("Main square 20").telephone("987456").pets(null).build());

        when(ownerRepository.findAll()).thenReturn(mockOwners);

        Set<Owner> owners = ownerJpaService.findAll();

        assertNotNull(owners);
        assertEquals(2, owners.size());
    }

    @Test
    void findById() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.empty());

        Owner owner = ownerJpaService.findById(OWNER_ID);

        assertNull(owner);
    }

    @Test
    void findByIdNotFound() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(mockOwner));

        Owner owner = ownerJpaService.findById(OWNER_ID);

        assertNotNull(owner);
    }


    @Test
    void save() {
        Owner mockOwner = Owner.builder().firstName("John").lastName("Doe").city("Boston").address("Elm street 1").telephone("1234567").pets(null).build();
        mockOwner.setId(OWNER_ID);

        when(ownerRepository.save(any())).thenReturn(mockOwner);

        Owner owner = ownerJpaService.save(mockOwner);

        verify(ownerRepository).save(any());
        assertNotNull(owner);
    }

    @Test
    void delete() {
        ownerJpaService.delete(mockOwner);

        verify(ownerRepository).delete(any());
    }

    @Test
    void deleteById() {
        ownerJpaService.deleteById(OWNER_ID);

        verify(ownerRepository).deleteById(anyLong());
    }

    @Test
    void findByLastName() {
        when(ownerRepository.findByLastName(any())).thenReturn(mockOwner);

        Owner owner = ownerJpaService.findByLastName(LAST_NAME);

        verify(ownerRepository).findByLastName(any());
        assertEquals(LAST_NAME, owner.getLastName());
    }
}