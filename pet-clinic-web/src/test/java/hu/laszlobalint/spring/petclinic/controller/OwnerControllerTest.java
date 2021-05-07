package hu.laszlobalint.spring.petclinic.controller;

import hu.laszlobalint.spring.petclinic.model.Owner;
import hu.laszlobalint.spring.petclinic.service.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    Set<Owner> mockOwners;
    Long ownerId = 1L;

    MockMvc mockMvc;

    @Mock
    OwnerService ownerService;

    @InjectMocks
    OwnerController ownerController;

    @BeforeEach
    void setUp() {
        mockOwners = new HashSet<>();
        mockOwners.add(Owner.builder().firstName("John").lastName("Doe").city("Boston").address("Elm street 1").telephone("1234567").pets(null).build());
        mockOwners.add(Owner.builder().firstName("Jane").lastName("Doe").city("Houston").address("Main square 20").telephone("987456").pets(null).build());

        ownerController = new OwnerController(ownerService);
        mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();
    }

    @Test
    void listAll() throws Exception {
        when(ownerService.findAll()).thenReturn(mockOwners);

        mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/index"))
                .andExpect(model().attribute("owners", hasSize(2)));
    }

    @Test
    void showById() throws Exception {
        Owner owner = new Owner();
        owner.setId(ownerId);
        when(ownerService.findById(anyLong())).thenReturn(owner);

        mockMvc.perform(get("/owners/123"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/owner-details"))
                .andExpect(model().attribute("owner", hasProperty("id", is(ownerId))));
    }

    @Test
    void find() throws Exception {
        mockMvc.perform(get("/owners/find"))
                .andExpect(status().isOk())
                .andExpect(view().name("error/not-implemented"));
    }
}