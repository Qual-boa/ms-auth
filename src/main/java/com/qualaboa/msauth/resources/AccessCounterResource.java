package com.qualaboa.msauth.resources;

import com.qualaboa.msauth.dataContract.dtos.Access.DashboardDataDTO;
import com.qualaboa.msauth.dataContract.entities.AccessCounter;
import com.qualaboa.msauth.services.AccessCounterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(AccessCounterResource.class);

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
        logger.info("Received request for file with establishmentId: {}", establishmentId);

        var file = service.fileCsv(establishmentId);
        logger.info("File retrieved: {}", file.getFilename());

        MediaType mediaType = MediaTypeFactory.getMediaType(file)
                .orElse(MediaType.APPLICATION_OCTET_STREAM);
        logger.info("Determined media type: {}", mediaType);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(mediaType);
        httpHeaders.setAccessControlExposeHeaders(List.of("Content-Disposition"));
        ContentDisposition disposition = ContentDisposition.attachment()
                .filename(file.getFilename())
                .build();
        logger.info("Content disposition set to: {}", disposition);

        httpHeaders.setContentDisposition(disposition);

        logger.info("Returning response with status OK");
        return new ResponseEntity<FileSystemResource>(file, httpHeaders, HttpStatus.OK);
    }

    @GetMapping(value = "/file/{establishmentId}/string")
    public ResponseEntity<String> createCsvFromString(@PathVariable UUID establishmentId) {
        try {
            String string = service.fileCsvFromString(establishmentId);
            return ResponseEntity.ok(string);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
