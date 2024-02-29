package com.qualaboa.msauth.services;

import com.qualaboa.msauth.dto.UserDTO;
import com.qualaboa.msauth.entities.User;
import com.qualaboa.msauth.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService implements IService<UserDTO, UUID> {

    @Autowired
    private UserRepository repository;

    @Transactional
    @Override
    public UserDTO create(UserDTO userDTO) {
        User entity = new User();
        copyDTOToEntity(userDTO, entity);
        entity = repository.save(entity);
        return new UserDTO(entity);
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
