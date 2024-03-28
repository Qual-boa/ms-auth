package com.qualaboa.msauth.services;

import com.qualaboa.msauth.dto.UserDTO;
import com.qualaboa.msauth.entities.User;
import com.qualaboa.msauth.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService implements IService<UserDTO, UUID> {

    @Autowired
    private UserRepository repository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public UserDTO create(UserDTO userDTO) {
        User entity = new User();
        copyDTOToEntity(userDTO, entity);
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        entity = repository.save(entity);
        return new UserDTO(entity);
    }

    @Override
    public void delete(UUID id) {
        repository.delete(repository.getReferenceById(id));
    }

    @Override
    public UserDTO update(UUID id, UserDTO userDTO) {
        User entity = new User();
        copyDTOToEntity(userDTO, entity);
        entity.setId(id);
        entity = repository.save(entity);
        return new UserDTO(entity);
    }

    @Override
    public List<UserDTO> findAll() {
        return repository.findAll().stream().map(UserDTO::new).toList();
    }

    @Transactional
    @Override
    public UserDTO findById(UUID id) {
        return new UserDTO(repository.getReferenceById(id));
    }

    private void copyDTOToEntity(UserDTO dto, User entity){
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
    }

}
