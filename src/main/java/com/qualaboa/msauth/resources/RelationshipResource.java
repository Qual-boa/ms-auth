package com.qualaboa.msauth.resources;

import com.qualaboa.msauth.dataContract.dtos.relationship.RelationshipResponseDTO;
import com.qualaboa.msauth.dataContract.dtos.relationship.RelationshipSearchDTO;
import com.qualaboa.msauth.services.RelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/relationship")
@CrossOrigin(origins = "*")
public class RelationshipResource {
    @Autowired
    private RelationshipService service;
    
    @GetMapping("/byEstablishmentId")
    public ResponseEntity<List<RelationshipResponseDTO>> byEstablishmentId(@RequestBody RelationshipSearchDTO request) {
        return ResponseEntity.ok().body(service.getRelationshipByEstablishmentId(request));
    }
    
    @GetMapping("/byEstablishmentIdAndInteractionType")
    public ResponseEntity<List<RelationshipResponseDTO>> byEstablishmentIdAndInteractionType (@RequestBody RelationshipSearchDTO request) {
        return ResponseEntity.ok().body(service.getRelationshipByEstablishmentIdAndInteractionType(request));
    }
    
    @GetMapping("/average/{id}")
    public ResponseEntity<Double> average(@PathVariable UUID id) {
        Double average = service.getEstablishmentAverageRate(id);
        if (average == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(average);
    }
}
