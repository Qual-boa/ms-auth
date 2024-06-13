package com.qualaboa.msauth.resources;

import com.qualaboa.msauth.dataContract.dtos.establishment.EstablishmentResponseDTO;
import com.qualaboa.msauth.dataContract.dtos.information.InformationCreateDTO;
import com.qualaboa.msauth.dataContract.dtos.information.InformationResponseDTO;
import com.qualaboa.msauth.dataContract.dtos.information.InformationUpdateDTO;
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
    public ResponseEntity<EstablishmentResponseDTO> create(@RequestBody InformationCreateDTO createDTO, @PathVariable UUID establishmentId){
        EstablishmentResponseDTO responseDTO = service.save(createDTO, establishmentId);
        return ResponseEntity.status(201).body(responseDTO);
    }
    
    @PutMapping("/establishment")
    public ResponseEntity<EstablishmentResponseDTO> update(@RequestBody InformationUpdateDTO request){
        EstablishmentResponseDTO responseDTO = service.update(request);
        return ResponseEntity.status(200).body(responseDTO);
    }
}
