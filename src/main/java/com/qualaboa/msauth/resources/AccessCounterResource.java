package com.qualaboa.msauth.resources;

import com.qualaboa.msauth.dataContract.dtos.Access.DashboardDataDTO;
import com.qualaboa.msauth.dataContract.entities.AccessCounter;
import com.qualaboa.msauth.services.AccessCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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

    @GetMapping(value = "/file/{establishmentId}", produces = "text/csv")
    public ResponseEntity<FileSystemResource> getFile(@PathVariable UUID establishmentId) throws IOException {
        var file = service.fileCsv(establishmentId);
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
