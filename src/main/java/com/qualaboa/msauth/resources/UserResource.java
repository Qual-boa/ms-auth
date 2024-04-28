package com.qualaboa.msauth.resources;

import com.qualaboa.msauth.config.TokenService;
import com.qualaboa.msauth.dto.UserCreateDTO;
import com.qualaboa.msauth.dto.LoginResponseDTO;
import com.qualaboa.msauth.dto.UserUpdateDTO;
import com.qualaboa.msauth.dto.UserResponseDTO;
import com.qualaboa.msauth.dto.user.AuthenticationDTO;
import com.qualaboa.msauth.entities.User;
import com.qualaboa.msauth.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserResource {

    @Autowired
    private UserService service;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User)auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> save(@RequestBody @Valid UserCreateDTO dto){
        if(this.service.loadUserByUsername(dto.getEmail()) != null) return ResponseEntity.badRequest().build();
        UserResponseDTO userResponseDTO = service.save(dto);
        return ResponseEntity.created(URI.create("/users")).body(userResponseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable UUID id){
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<UserResponseDTO>> findAll(Pageable pageable){
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(@PathVariable UUID id, @RequestBody UserUpdateDTO dto){
        UserResponseDTO userResponseDTO = service.update(dto, id);
        return ResponseEntity.ok(userResponseDTO);
    }

}
