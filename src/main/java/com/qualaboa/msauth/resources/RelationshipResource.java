package com.qualaboa.msauth.resources;

import com.qualaboa.msauth.dataContract.dtos.relationship.RelationshipResponseDTO;
import com.qualaboa.msauth.dataContract.dtos.relationship.RelationshipSearchDTO;
import com.qualaboa.msauth.services.RelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/relationship")
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
}
