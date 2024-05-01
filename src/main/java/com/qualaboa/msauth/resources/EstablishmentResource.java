package com.qualaboa.msauth.resources;

import com.qualaboa.msauth.dataContract.dtos.establishment.EstablishmentCreateDTO;
import com.qualaboa.msauth.dataContract.dtos.establishment.EstablishmentResponseDTO;
import com.qualaboa.msauth.dataContract.dtos.establishment.EstablishmentSearchDto;
import com.qualaboa.msauth.services.EstablishmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

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

    @GetMapping("/listbyfilters")
    public ResponseEntity<List<EstablishmentResponseDTO>> getListByfilters(@RequestBody EstablishmentSearchDto request){
        List<EstablishmentResponseDTO> responseDTO = service.findListByFilters(request);
        if(responseDTO == null) return ResponseEntity.noContent().build();

        return ResponseEntity.ok(responseDTO);
    }
}
