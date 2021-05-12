package hu.laszlobalint.spring.petclinic.controller;

import hu.laszlobalint.spring.petclinic.model.Owner;
import hu.laszlobalint.spring.petclinic.model.Pet;
import hu.laszlobalint.spring.petclinic.model.PetType;
import hu.laszlobalint.spring.petclinic.service.PetService;
import hu.laszlobalint.spring.petclinic.service.VisitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.UriTemplate;

import java.net.URI;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class VisitControllerTest {

    MockMvc mockMvc;
    UriTemplate visitsUriTemplate = new UriTemplate("/owners/{ownerId}/pets/{petId}/visits/new");
    Map<String, String> uriVariables = new HashMap<>();
    URI visitsUri;

    @Mock
    PetService petService;

    @Mock
    VisitService visitService;

    @InjectMocks
    VisitController visitController;

    @BeforeEach
    void setUp() {
        Long petId = 1L;
        Long ownerId = 1L;
        Pet pet = Pet.builder()
                .birthDate(LocalDate.of(2018, 11, 13))
                .name("Cutie")
                .visits(new HashSet<>())
                .owner(Owner.builder()
                        .lastName("Perfect")
                        .firstName("Lary")
                        .build())
                .petType(PetType.builder()
                        .name("Dog").build())
                .build();
        pet.setId(petId);
        pet.getOwner().setId(ownerId);

        when(petService.findById(anyLong()))
                .thenReturn(pet);

        uriVariables.clear();
        uriVariables.put("ownerId", ownerId.toString());
        uriVariables.put("petId", petId.toString());
        visitsUri = visitsUriTemplate.expand(uriVariables);

        mockMvc = MockMvcBuilders
                .standaloneSetup(visitController)
                .build();
    }

    @Test
    void showNewVisitForm() throws Exception {
        mockMvc.perform(get(visitsUri))
                .andExpect(status().isOk())
                .andExpect(view().name("visits/create-or-update-form"));
    }

    @Test
    void processNewVisitForm() throws Exception {
        mockMvc.perform(post(visitsUri)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("date", "2020-11-11")
                .param("description", "Another regular visit at the vet."))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name(String.format("redirect:/owners/%s", 1L)))
                .andExpect(model().attributeExists("visit"));
    }
}