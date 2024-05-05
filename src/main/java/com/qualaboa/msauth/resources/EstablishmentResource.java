package com.qualaboa.msauth.resources;

import com.qualaboa.msauth.dataContract.dtos.establishment.EstablishmentCreateDTO;
import com.qualaboa.msauth.dataContract.dtos.establishment.EstablishmentResponseDTO;
import com.qualaboa.msauth.dataContract.dtos.establishment.EstablishmentSearchDTO;
import com.qualaboa.msauth.dataContract.dtos.establishment.EstablishmentUpdateDTO;
import com.qualaboa.msauth.services.EstablishmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.UUID;

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

    @GetMapping
    public ResponseEntity<List<EstablishmentResponseDTO>> getAll(){
        List<EstablishmentResponseDTO> responseDTO = service.findAll();
        if(responseDTO == null) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/listbyfilters")
    public ResponseEntity<List<EstablishmentResponseDTO>> getListByfilters(@RequestBody EstablishmentSearchDTO request){
        List<EstablishmentResponseDTO> responseDTO = service.findListByFilters(request);
        if(responseDTO == null) return ResponseEntity.noContent().build();

        return ResponseEntity.ok(responseDTO);
    }
    
    @PutMapping
    public ResponseEntity<EstablishmentResponseDTO> update(@RequestBody @Valid EstablishmentUpdateDTO dto){
        EstablishmentResponseDTO responseDTO = service.update(dto);
        return ResponseEntity.ok(responseDTO);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping(value = "/file", produces = "text/csv")
    public ResponseEntity<FileSystemResource> getFile(@RequestBody EstablishmentSearchDTO request) throws IOException {
        var file = service.fileCsv(request);
        MediaType mediaType = MediaTypeFactory
                .getMediaType(file)
                .orElse(MediaType.APPLICATION_OCTET_STREAM);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(mediaType);
        ContentDisposition disposition = ContentDisposition
                .attachment()
                .filename(file.getFilename())
                .build();
        httpHeaders.setContentDisposition(disposition);
        return new ResponseEntity<>(file, httpHeaders, HttpStatus.OK);
    }
}