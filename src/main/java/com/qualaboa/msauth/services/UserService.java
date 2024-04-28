package com.qualaboa.msauth.services;

import com.qualaboa.msauth.dto.UserCreateDTO;
import com.qualaboa.msauth.dto.UserUpdateDTO;
import com.qualaboa.msauth.dto.UserResponseDTO;
import com.qualaboa.msauth.entities.User;
import com.qualaboa.msauth.mappers.UserMapper;
import com.qualaboa.msauth.repositories.UserRepository;
import com.qualaboa.msauth.services.exceptions.ResourceNotFoundException;
import com.qualaboa.msauth.services.interfaces.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserService implements IServiceSave<UserCreateDTO, UserResponseDTO>,
                                    IServiceFindAll<UserResponseDTO>,
                                    IServiceDelete<UUID>,
                                    IServiceUpdate<UserUpdateDTO, UserResponseDTO, UUID>,
                                    IServiceFindById<UserResponseDTO, UUID>,
                                    UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.findByEmail(email);
    }

    @Override
    @Transactional
    public void delete(UUID uuid) {
        repository.delete(repository.findById(uuid).orElseThrow(()
                -> new ResourceNotFoundException("Resource not found")));
    }

    @Override
    @Transactional
    public Page<UserResponseDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(UserResponseDTO::new);
    }

    @Override
    @Transactional
    public UserResponseDTO findById(UUID uuid) {
        User entity = repository.findById(uuid).orElseThrow(()
                -> new ResourceNotFoundException("Resource not found"));
        return new UserResponseDTO(entity);
    }

    @Override
    @Transactional
    public UserResponseDTO save(UserCreateDTO userCreateDTO) {
        User entity = UserMapper.toEntity(userCreateDTO);

        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        entity.setCreatedAt(LocalDateTime.now());
        entity = repository.save(entity);
        return new UserResponseDTO(entity);
    }

    @Override
    @Transactional
    public UserResponseDTO update(UserUpdateDTO request, UUID uuid) {
        User entity = repository.findById(uuid).orElseThrow(()
                -> new ResourceNotFoundException("Resource not found"));
        entity = UserMapper.toEntity(request, entity);

        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        entity = repository.save(entity);
        return new UserResponseDTO(entity);
    }

}
