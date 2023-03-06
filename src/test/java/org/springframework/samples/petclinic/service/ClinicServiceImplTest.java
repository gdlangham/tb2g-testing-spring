package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.repository.OwnerRepository;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.samples.petclinic.repository.VetRepository;
import org.springframework.samples.petclinic.repository.VisitRepository;

@ExtendWith(MockitoExtension.class)
class ClinicServiceImplTest {

    @Mock
    PetRepository petRepository;
    @Mock
    VetRepository vetRepository;
    @Mock
    OwnerRepository ownerRepository;
    @Mock
    VisitRepository visitRepository;

    @InjectMocks
    ClinicServiceImpl clinicService;

    @Test
    void findPetTypes() {
        // given
        List<PetType> petTypes = new ArrayList<>();
        petTypes.add(new PetType());
        given(petRepository.findPetTypes()).willReturn(petTypes);
        // when
        Collection<PetType> resultList = clinicService.findPetTypes();
        // then
        verify(petRepository).findPetTypes();
        then(petRepository).should().findPetTypes();
        assertThat(resultList).isNotNull();
        assertThat(resultList).hasSize(1);
    }

    @Test
    void findPetTypesThrowsDataAccessException() {
        // given
        given(petRepository.findPetTypes()).willThrow(new EmptyResultDataAccessException("Bad data", -1));
        // when
        EmptyResultDataAccessException exc = assertThrows(EmptyResultDataAccessException.class, () -> {
            clinicService.findPetTypes();
        }, "DataAccessException was thrown");
        // then
        assertEquals("Bad data", exc.getMessage());

    }
}