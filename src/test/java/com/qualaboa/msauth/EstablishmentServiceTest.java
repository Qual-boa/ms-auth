package com.qualaboa.msauth;
// Path: src/test/java/com/example/yourpackage/service/EstablishmentServiceTest.java
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import com.qualaboa.msauth.dataContract.entities.Establishment;
import com.qualaboa.msauth.dataContract.entities.Category;
import com.qualaboa.msauth.dataContract.entities.Relationship;
import com.qualaboa.msauth.dataContract.entities.Information;
import com.qualaboa.msauth.dataContract.entities.AccessCounter;
import com.qualaboa.msauth.repositories.EstablishmentRepository;
import com.qualaboa.msauth.mappers.EstablishmentMapper;
import com.qualaboa.msauth.dataContract.dtos.establishment.EstablishmentResponseDTO;
import com.qualaboa.msauth.services.EstablishmentService;

@ExtendWith(MockitoExtension.class)
public class EstablishmentServiceTest {

    @Mock
    private EstablishmentRepository repository;

    @Mock
    private EstablishmentMapper mapper;

    @InjectMocks
    private EstablishmentService service;

    private Queue<Establishment> establishmentQueue;

    @BeforeEach
    void setUp() {
        establishmentQueue = new LinkedList<>();
        establishmentQueue.add(new Establishment(
                UUID.randomUUID(),
                "Establishment 1",
                "12345678901234",
                100,
                LocalDateTime.now(),
                LocalDateTime.now(),
                Arrays.asList(new Category()),
                Arrays.asList(new Relationship()),
                new Information(),
                Arrays.asList(new AccessCounter())
        ));
        establishmentQueue.add(new Establishment(
                UUID.randomUUID(),
                "Establishment 2",
                "23456789012345",
                200,
                LocalDateTime.now(),
                LocalDateTime.now(),
                Arrays.asList(new Category()),
                Arrays.asList(new Relationship()),
                new Information(),
                Arrays.asList(new AccessCounter())
        ));
    }

    @Test
    @Transactional(readOnly = true)
    void findAll_ShouldReturnListOfEstablishmentResponseDTO() {
        List<Establishment> entities = Arrays.asList(establishmentQueue.toArray(new Establishment[0]));
        List<EstablishmentResponseDTO> expectedDtos = Arrays.asList(
                new EstablishmentResponseDTO(),
                new EstablishmentResponseDTO()
        );

        when(repository.findAll()).thenReturn(entities);
        when(mapper.toDto(entities)).thenReturn(expectedDtos);

        List<EstablishmentResponseDTO> actualDtos = service.findAll();

        assertNotNull(actualDtos);
        assertEquals(expectedDtos.size(), actualDtos.size());
        assertEquals(expectedDtos, actualDtos);

        verify(repository, times(1)).findAll();
        verify(mapper, times(1)).toDto(entities);
    }
}
