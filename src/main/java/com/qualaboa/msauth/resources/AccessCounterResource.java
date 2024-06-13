package com.qualaboa.msauth.resources;

import com.qualaboa.msauth.dataContract.dtos.Access.DashboardDataDTO;
import com.qualaboa.msauth.dataContract.entities.AccessCounter;
import com.qualaboa.msauth.services.AccessCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/access")
@CrossOrigin(origins = "*")
public class AccessCounterResource {
    @Autowired
    private AccessCounterService service;
    
    @GetMapping("/{id}")
    private ResponseEntity<List<AccessCounter>> getAccessCounterByEstablishmentId(@PathVariable UUID id) {
        List<AccessCounter> accessCounters = service.getAccessCounterByEstablishmentId(id);
        if (accessCounters.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(accessCounters);
    }

    @GetMapping("/dashboard/{id}")
    private ResponseEntity<DashboardDataDTO> getDashboardDataByEstablishmentId(@PathVariable UUID id) {
        DashboardDataDTO dashboardData = service.getDashboardDataByEstablishmentId(id);
        return ResponseEntity.ok(dashboardData);
    }
}
