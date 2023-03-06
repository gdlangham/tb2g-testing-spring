package org.springframework.samples.petclinic.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Vets;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.ui.Model;

@ExtendWith(MockitoExtension.class)
class VetControllerTest {

    @Mock
    ClinicService clinicService;

    @Mock
    Map<String, Object> model;

    @InjectMocks
    VetController vetController;

    Vets vets;

    @BeforeEach
    void setup() {
        vets = new Vets();
        Vet vet = new Vet();
        vet.setFirstName("Johnny");
        vet.setLastName("B Good");
        Specialty s = new Specialty();
        s.setName("horses");
        vet.addSpecialty(s);
        vets.getVetList().add(vet);
    }
    @Test
    void showVetList() {
        // given
        given(clinicService.findVets()).willReturn(vets.getVetList());
        // when
        String page = vetController.showVetList(model);
        // then
        then(clinicService).should().findVets();
        then(model).should().put(anyString(), any());
        assertThat(page).isEqualToIgnoringCase("vets/vetList");
        assertThat(model).isNotEmpty();
    }

    @Test
    void showResourcesVetList() {
        // given
        given(clinicService.findVets()).willReturn(vets.getVetList());
        // when
        Vets vets = vetController.showResourcesVetList();
        // then
        then(clinicService).should().findVets();
        assertNotNull(vets);
        assertThat(vets.getVetList()).hasSize(1);
    }
}