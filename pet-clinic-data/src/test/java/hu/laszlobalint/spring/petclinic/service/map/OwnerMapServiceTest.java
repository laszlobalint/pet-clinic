package hu.laszlobalint.spring.petclinic.service.map;

import hu.laszlobalint.spring.petclinic.model.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OwnerMapServiceTest {

    OwnerMapService ownerMapService;
    Owner owner;

    final Long OWNER_ID = 1L;
    final String LAST_NAME = "Doe";

    @BeforeEach
    void setUp() {
        ownerMapService = new OwnerMapService(new PetMapService(), new PetTypeMapService());
        owner = Owner.builder().firstName("John").lastName("Doe").city("Boston").address("Elm street 1").telephone("1234567").pets(null).build();
        owner.setId(1L);
        ownerMapService.save(owner);
    }

    @Test
    void findAll() {
        Set<Owner> owners = ownerMapService.findAll();

        assertEquals(1, owners.size());
    }

    @Test
    void findById() {
        Owner owner = ownerMapService.findById(OWNER_ID);

        assertEquals(OWNER_ID, owner.getId());
    }

    @Test
    void saveWithId() {
        Long id = 2L;
        Owner owner = Owner.builder().firstName("Jane").lastName("Doe").city("Houston").address("Main square 20").telephone("987456").pets(null).build();
        owner.setId(id);
        Owner saved = ownerMapService.save(owner);

        assertEquals(id, saved.getId());
    }

    @Test
    void saveWithoutId() {
        Owner owner = Owner.builder().firstName("Jane").lastName("Doe").city("Houston").address("Main square 20").telephone("987456").pets(null).build();
        Owner saved = ownerMapService.save(owner);

        assertNotNull(saved);
        assertNotNull(saved.getId());
    }

    @Test
    void delete() {
        ownerMapService.delete(ownerMapService.findById(OWNER_ID));

        assertEquals(0, ownerMapService.findAll().size());
    }

    @Test
    void deleteById() {
        ownerMapService.deleteById(OWNER_ID);

        assertEquals(0, ownerMapService.findAll().size());
    }

    @Test
    void findByLastName() {
        Owner owner = ownerMapService.findByLastName(LAST_NAME);

        assertNotNull(owner);
        assertEquals(OWNER_ID, owner.getId());
    }
}