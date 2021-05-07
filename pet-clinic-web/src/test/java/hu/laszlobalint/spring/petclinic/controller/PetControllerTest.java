package hu.laszlobalint.spring.petclinic.controller;

import hu.laszlobalint.spring.petclinic.model.Owner;
import hu.laszlobalint.spring.petclinic.model.Pet;
import hu.laszlobalint.spring.petclinic.model.PetType;
import hu.laszlobalint.spring.petclinic.service.OwnerService;
import hu.laszlobalint.spring.petclinic.service.PetService;
import hu.laszlobalint.spring.petclinic.service.PetTypeService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PetControllerTest {

    MockMvc mockMvc;
    Owner mockOwner;
    Long ownerId = 1L;
    Set<PetType> petTypes;
    PetType cat;
    PetType dog;

    @Mock
    OwnerService ownerService;

    @Mock
    PetService petService;

    @Mock
    PetTypeService petTypeService;

    @InjectMocks
    PetController petController;

    @BeforeEach
    void setUp() {
        mockOwner = Owner.builder().firstName("Test").lastName("Breaker").city("Stack City").address("Exception 666").telephone("00112233").pets(null).build();
        mockOwner.setId(ownerId);

        petTypes = new HashSet<>();
        dog = new PetType("Dog");
        dog.setId(1L);
        cat = new PetType("Cat");
        cat.setId(2L);
        petTypes.add(dog);
        petTypes.add(cat);

        mockMvc = MockMvcBuilders.standaloneSetup(petController).build();
    }

    @Test
    void showCreateForm() throws Exception {
        when(ownerService.findById(anyLong())).thenReturn(mockOwner);
        when(petTypeService.findAll()).thenReturn(petTypes);

        mockMvc.perform(get(String.format("/owners/%s/pets/new", 1L)))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("owner"))
                .andExpect(model().attributeExists("pet"))
                .andExpect(view().name("pets/create-or-update-form"));
    }

    @Test
    void processCreateForm() throws Exception {
        when(ownerService.findById(anyLong())).thenReturn(mockOwner);
        when(petTypeService.findAll()).thenReturn(petTypes);

        mockMvc.perform(post(String.format("/owners/%s/pets/new", 1L)))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(String.format("redirect:/owners/%s", 1L)));

        verify(petService).save(any());
    }

    @Test
    void showUpdateForm() throws Exception {
        when(ownerService.findById(anyLong())).thenReturn(mockOwner);
        when(petTypeService.findAll()).thenReturn(petTypes);
        when(petService.findById(anyLong())).thenReturn(Pet.builder().build());

        mockMvc.perform(get(String.format("/owners/%s/pets/%s/edit", 1L, 2L)))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("owner"))
                .andExpect(model().attributeExists("pet"))
                .andExpect(view().name("pets/create-or-update-form"));
    }

    @Test
    void processUpdateForm() throws Exception {
        when(ownerService.findById(anyLong())).thenReturn(mockOwner);
        when(petTypeService.findAll()).thenReturn(petTypes);

        mockMvc.perform(post(String.format("/owners/%s/pets/%s/edit", 1L, 2L)))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(String.format("redirect:/owners/%s", 1L)));

        verify(petService).save(any());
    }
}