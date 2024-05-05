package com.qualaboa.msauth.resources;

import com.qualaboa.msauth.dataContract.dtos.address.AddressRequestDTO;
import com.qualaboa.msauth.dataContract.dtos.address.AddressResponseDTO;
import com.qualaboa.msauth.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/address")
public class AddressResource {

    @Autowired
    private AddressService service;

    @GetMapping("/{id}")
    public ResponseEntity<AddressResponseDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<AddressResponseDTO> saveWithUser(@RequestBody AddressRequestDTO createDTO, @PathVariable UUID userId) {
        AddressResponseDTO dto = service.saveWithUser(createDTO, userId);
        return ResponseEntity.status(201).body(dto);
    }

    @PostMapping("/establishment/{establishmentId}")
    public ResponseEntity<AddressResponseDTO> saveWithEstablishment(@RequestBody AddressRequestDTO createDTO, @PathVariable UUID establishmentId) {
        AddressResponseDTO dto = service.saveWithEstablishment(createDTO, establishmentId);
        return ResponseEntity.status(201).body(dto);
    }

    @GetMapping("/establishment/{establishmentId}")
    public ResponseEntity<List<AddressResponseDTO>> findByEstablishment(@PathVariable UUID establishmentId) {
        List<AddressResponseDTO> res = service.findByEstablishment(establishmentId);
        return res.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok().body(res);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AddressResponseDTO>> findByUser(@PathVariable UUID userId) {
        List<AddressResponseDTO> responseDTO = service.findByUser(userId);
        return responseDTO.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteByAddress(@PathVariable UUID id){
        service.deleteByAddress(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/establishment/{id}")
    public ResponseEntity<Void> deleteAllByEsblishmentId(@PathVariable UUID id){
        service.deleteByEstablishment(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> deleteAllByUserId(@PathVariable UUID id){
        service.deleteByUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressResponseDTO> update(@PathVariable UUID id, @RequestBody AddressRequestDTO createDTO) {
        AddressResponseDTO dto = service.update(createDTO, id);
        return ResponseEntity.ok(dto);
    }
}
