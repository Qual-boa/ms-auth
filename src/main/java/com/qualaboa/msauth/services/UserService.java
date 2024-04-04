package com.qualaboa.msauth.services;

import com.qualaboa.msauth.dto.CreateUserRequest;
import com.qualaboa.msauth.dto.UpdateUserRequest;
import com.qualaboa.msauth.dto.UserResponse;
import com.qualaboa.msauth.entities.User;
import com.qualaboa.msauth.repositories.UserRepository;
import com.qualaboa.msauth.services.exceptions.ResourceNotFoundException;
import com.qualaboa.msauth.services.interfaces.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserService implements IServiceSave<CreateUserRequest, UserResponse>, IServiceFindAll<UserResponse>, IServiceDelete<UUID>, IServiceUpdate<UpdateUserRequest, UserResponse, UUID>, IServiceFindById<UserResponse, UUID> {

    @Autowired
    private UserRepository repository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Override
    @Transactional
    public void delete(UUID uuid) {
        repository.delete(repository.findById(uuid).orElseThrow(() -> new ResourceNotFoundException("Resource not found")));
    }

    @Override
    @Transactional
    public Page<UserResponse> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(UserResponse::new);
    }

    @Override
    @Transactional
    public UserResponse findById(UUID uuid) {
        User entity = repository.findById(uuid).orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
        return new UserResponse(entity);
    }

    @Override
    @Transactional
    public UserResponse save(CreateUserRequest createUserRequest) {
        User entity = new User();
        copyDTOToEntity(createUserRequest, entity);
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        entity.setCreatedAt(LocalDateTime.now());
        entity = repository.save(entity);
        return new UserResponse(entity);
    }

    @Override
    @Transactional
    public UserResponse update(UpdateUserRequest updateUserRequest, UUID uuid) {
        User entity = repository.findById(uuid).orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
        copyDTOToEntity(updateUserRequest, entity);
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        entity.setUpdatedAt(LocalDateTime.now());
        entity = repository.save(entity);
        return new UserResponse(entity);
    }

    private void copyDTOToEntity(CreateUserRequest createUserRequest, User entity) {
        entity.setName(createUserRequest.getName());
        entity.setEmail(createUserRequest.getEmail());
        entity.setPassword(createUserRequest.getPassword());
        entity.setUserTypeEnum(createUserRequest.getUserTypeEnum());
        entity.setRoleEnum(createUserRequest.getRoleEnum());
    }

    private void copyDTOToEntity(UpdateUserRequest updateUserRequest, User entity) {
        entity.setName(updateUserRequest.getName());
        entity.setEmail(updateUserRequest.getEmail());
        entity.setPassword(updateUserRequest.getPassword());
    }

}
