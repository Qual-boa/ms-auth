package com.qualaboa.msauth.services;

import com.qualaboa.msauth.dataContract.dtos.address.AddressRequestDTO;
import com.qualaboa.msauth.dataContract.dtos.address.AddressResponseDTO;
import com.qualaboa.msauth.dataContract.dtos.establishment.EstablishmentResponseDTO;
import com.qualaboa.msauth.dataContract.dtos.user.UserResponseDTO;
import com.qualaboa.msauth.dataContract.entities.Address;
import com.qualaboa.msauth.dataContract.entities.Establishment;
import com.qualaboa.msauth.dataContract.entities.User;
import com.qualaboa.msauth.mappers.AddressMapper;
import com.qualaboa.msauth.repositories.AddressRepository;
import com.qualaboa.msauth.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AddressService {

    @Autowired
    private AddressRepository repository;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressMapper mapper;

    @Autowired
    private EstablishmentService establishmentService;

    @Transactional(readOnly = true)
    public AddressResponseDTO findById(UUID id) {
        Address entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Resource Not Found"));
        return (AddressResponseDTO) mapper.toDto(entity);
    }

    @Transactional
    public AddressResponseDTO saveWithUser(AddressRequestDTO createDTO, UUID userId) {
        UserResponseDTO userDTO = userService.findById(userId);
        Address entity = mapper.toEntity(createDTO);
        User userEntity = new User();
        userEntity.setId(userDTO.getId());
        entity.setUser(userEntity);
        entity.setCreatedAt(LocalDateTime.now());
        entity = repository.save(entity);
        return (AddressResponseDTO) mapper.toDto(entity);
    }

    @Transactional
    public AddressResponseDTO saveWithEstablishment(AddressRequestDTO createDTO, UUID establishmentId) {
        EstablishmentResponseDTO establishmentResponseDTO = establishmentService.findById(establishmentId);
        Establishment establishmentEntity = new Establishment();
        establishmentEntity.setId(establishmentResponseDTO.getId());
        Address entity = mapper.toEntity(createDTO);
        entity.setEstablishment(establishmentEntity);
        entity.setCreatedAt(LocalDateTime.now());
        entity = repository.save(entity);
        return (AddressResponseDTO) mapper.toDto(entity);
    }

    @Transactional(readOnly = true)
    public List<AddressResponseDTO> findByEstablishment(UUID establishmentId){
        EstablishmentResponseDTO responseDTO = establishmentService.findById(establishmentId);
        Establishment establishment = new Establishment();
        establishment.setId(responseDTO.getId());
        List<Address> list = repository.findByEstablishment(establishment);
        return list.stream().map(x -> (AddressResponseDTO) mapper.toDto(x)).toList();
    }


    @Transactional(readOnly = true)
    public List<AddressResponseDTO> findByUser(UUID userId) {
        UserResponseDTO responseDTO = userService.findById(userId);
        User user = new User();
        user.setId(responseDTO.getId());
        List<Address> list = repository.findByUser(user);
        return list.stream().map(x -> (AddressResponseDTO) mapper.toDto(x)).toList();
    }

    @Transactional
    public void deleteByAddress(UUID addressId) {
        if(repository.existsById(addressId))
            repository.deleteById(addressId);
        else
            throw new ResourceNotFoundException("Resource Not Found");
    }

    @Transactional
    public void deleteByEstablishment(UUID establishmentId){
        if(!repository.existsByEstablishmentId(establishmentId)) throw new ResourceNotFoundException("Resource Not Found");
        EstablishmentResponseDTO responseDTO = establishmentService.findById(establishmentId);
        Establishment establishment = new Establishment();
        establishment.setId(responseDTO.getId());
        repository.deleteAllByEstablishment(establishment);
    }

    @Transactional
    public void deleteByUser(UUID userId){
        if(!repository.existsByUserId(userId)) throw new ResourceNotFoundException("Resource Not Found");
        UserResponseDTO responseDTO = userService.findById(userId);
        User user = new User();
        user.setId(responseDTO.getId());
        repository.deleteAllByUser(user);
    }

    @Transactional
    public AddressResponseDTO update(AddressRequestDTO createDTO, UUID id){
        Address entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Resource Not Found"));
        Address newEntity = mapper.toEntity(createDTO);
        newEntity.setUser(entity.getUser());
        newEntity.setEstablishment(entity.getEstablishment());
        newEntity.setUpdatedAt(LocalDateTime.now());
        newEntity.setId(entity.getId());
        newEntity.setCreatedAt(entity.getCreatedAt());
        newEntity = repository.save(newEntity);
        return ((AddressResponseDTO) mapper.toDto(newEntity));
    }
}
