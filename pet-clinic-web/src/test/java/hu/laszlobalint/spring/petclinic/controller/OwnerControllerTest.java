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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    Set<Owner> mockOwners;
    Owner mockOwner;
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

        mockOwner = Owner.builder().firstName("Test").lastName("Breaker").city("Stack City").address("Exception 666").telephone("00112233").pets(null).build();
        mockOwner.setId(ownerId);

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
        when(ownerService.findById(anyLong())).thenReturn(mockOwner);

        mockMvc.perform(get("/owners/123"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/owner-details"))
                .andExpect(model().attribute("owner", hasProperty("id", is(ownerId))));
    }

    @Test
    void findOwnersForm() throws Exception {
        mockMvc.perform(get("/owners/find"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/find-owners"))
                .andExpect(model().attributeExists("owner"));
    }

    @Test
    void findOwnersWithSingleResult() throws Exception {
        when(ownerService.findAllByLastNameLike(anyString())).thenReturn(Collections.singletonList(mockOwner));

        mockMvc.perform(get("/owners/searching"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(String.format("redirect:/owners/%s", ownerId)));
    }

    @Test
    void findOwnersWithMultipleResult() throws Exception {
        when(ownerService.findAllByLastNameLike(anyString())).thenReturn(new ArrayList<>(mockOwners));

        mockMvc.perform(get("/owners/searching"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/owners-list"))
                .andExpect(model().attribute("result", hasSize(2)));
    }

    @Test
    void findOwnersWithEmptyString() throws Exception {
        when(ownerService.findAllByLastNameLike(anyString())).thenReturn(new ArrayList<>(mockOwners));

        mockMvc.perform(get("/owners/searching").param("lastName", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/owners-list"))
                .andExpect(model().attribute("result", hasSize(2)));
    }

    @Test
    void showCreateForm() throws Exception {
        mockMvc.perform(get("/owners/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/create-or-update-form"))
                .andExpect(model().attributeExists("owner"));
    }

    @Test
    void processCreateForm() throws Exception {
        mockMvc.perform(post("/owners/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/create-or-update-form"))
                .andExpect(model().attributeHasErrors("owner"));
    }

    @Test
    void showUpdateForm() throws Exception {
        when(ownerService.findById(anyLong())).thenReturn(mockOwner);

        mockMvc.perform(get(String.format("/owners/%s/edit", ownerId)))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/create-or-update-form"))
                .andExpect(model().attributeExists("owner"));
    }

    @Test
    void processUpdateForm() throws Exception {
        mockMvc.perform(post(String.format("/owners/%s/edit", ownerId)))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/create-or-update-form"))
                .andExpect(model().attributeHasErrors("owner"));
    }
}