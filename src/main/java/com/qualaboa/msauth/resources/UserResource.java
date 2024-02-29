package com.qualaboa.msauth.resources;

import com.qualaboa.msauth.dto.UserDTO;
import com.qualaboa.msauth.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserResource {

    @Autowired
    private UserService service;

    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody @Valid UserDTO dto){
        dto = service.create(dto);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable UUID id){
        return ResponseEntity.ok(service.findById(id));
    }
}
