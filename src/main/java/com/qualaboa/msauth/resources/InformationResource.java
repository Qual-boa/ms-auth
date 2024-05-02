package com.qualaboa.msauth.resources;

import com.qualaboa.msauth.dataContract.dtos.information.InformationCreateDTO;
import com.qualaboa.msauth.dataContract.dtos.information.InformationResponseDTO;
import com.qualaboa.msauth.services.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/informations")
public class InformationResource {

    @Autowired
    private InformationService service;

    @PostMapping("/establishment/{establishmentId}")
    public ResponseEntity<InformationResponseDTO> create(@RequestBody InformationCreateDTO createDTO, @PathVariable UUID establishmentId){
        InformationResponseDTO responseDTO = service.save(createDTO, establishmentId);
        return ResponseEntity.status(201).body(responseDTO);
    }
}
