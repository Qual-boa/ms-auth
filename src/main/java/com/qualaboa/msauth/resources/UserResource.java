package com.qualaboa.msauth.resources;

import com.qualaboa.msauth.dto.CreateUserRequest;
import com.qualaboa.msauth.dto.UpdateUserRequest;
import com.qualaboa.msauth.dto.UserResponse;
import com.qualaboa.msauth.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserResource {

    @Autowired
    private UserService service;

    @PostMapping
    public ResponseEntity<UserResponse> save(@RequestBody @Valid CreateUserRequest dto){
        UserResponse userResponse = service.save(dto);
        return ResponseEntity.created(URI.create("/users")).body(userResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable UUID id){
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<UserResponse>> findAll(Pageable pageable){
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable UUID id, @RequestBody UpdateUserRequest dto){
        UserResponse userResponse = service.update(dto, id);
        return ResponseEntity.ok(userResponse);
    }

}
