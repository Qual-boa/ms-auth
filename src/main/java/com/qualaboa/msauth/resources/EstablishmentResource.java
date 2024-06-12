package com.qualaboa.msauth.resources;

import com.qualaboa.msauth.dataContract.dtos.establishment.*;
import com.qualaboa.msauth.dataContract.dtos.relationship.RelationshipCreateDTO;
import com.qualaboa.msauth.services.EstablishmentService;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
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
    
    @GetMapping("{id}")
    public ResponseEntity<EstablishmentResponseDTO> getById(@PathVariable UUID id){
        EstablishmentResponseDTO responseDTO = service.findById(id);
        if(responseDTO == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/listbyfilters")
    public ResponseEntity<List<EstablishmentResponseDTO>> getListByfilters(@RequestBody EstablishmentSearchDTO request) throws IOException{
        List<EstablishmentResponseDTO> responseDTO = service.findListByFilters(request);
        if(responseDTO == null || responseDTO.isEmpty()) return ResponseEntity.noContent().build();

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
    
    @PutMapping("/categories")
    public ResponseEntity<EstablishmentResponseDTO> createCategory(@RequestBody EstablishmentCategoryDTO dto) throws BadRequestException {
        EstablishmentResponseDTO responseDTO = service.saveCategories(dto);
        return ResponseEntity.ok(responseDTO);
    }
    
    @PutMapping("/relationship")
    public ResponseEntity<EstablishmentResponseDTO> createRelationship(@RequestBody RelationshipCreateDTO dto){
        EstablishmentResponseDTO responseDTO = service.saveRelationship(dto);
        return ResponseEntity.ok(responseDTO);
    }
}
