package com.qualaboa.msauth.resources;

import com.qualaboa.msauth.dto.establishment.EstablishmentCreateDTO;
import com.qualaboa.msauth.dto.establishment.EstablishmentResponseDTO;
import com.qualaboa.msauth.services.EstablishmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/establishments")
public class EstablishmentResource {

    @Autowired
    private EstablishmentService service;

    @PostMapping
    public ResponseEntity<EstablishmentResponseDTO> save(@RequestBody @Valid EstablishmentCreateDTO dto){
        EstablishmentResponseDTO responseDTO = service.save(dto);
        return ResponseEntity.created(URI.create("/establishments")).body(responseDTO);
    }
}
