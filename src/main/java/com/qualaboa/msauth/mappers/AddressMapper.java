package com.qualaboa.msauth.mappers;

import com.qualaboa.msauth.dataContract.dtos.address.AddressRequestDTO;
import com.qualaboa.msauth.dataContract.dtos.address.AddressResponseDTO;
import com.qualaboa.msauth.dataContract.entities.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper implements IMapper<Address> {
    @Override
    public Address toEntity(Object obj) {
        if(obj == null) return null;
        Address entity = new Address();
        AddressRequestDTO dto = (AddressRequestDTO) obj;
        entity.setStreet(dto.getStreet());
        entity.setNumber(dto.getNumber());
        entity.setPostalCode(dto.getPostalCode());
        entity.setNeighborhood(dto.getNeighborhood());
        entity.setComplement(dto.getComplement());
        entity.setCity(dto.getCity());
        entity.setState(dto.getState());
        return entity;
    }

    @Override
    public Object toDto(Address address) {
        if(address == null) return null;
        AddressResponseDTO dto = new AddressResponseDTO(address.getId(),
                address.getStreet(),
                address.getNumber(),
                address.getPostalCode(),
                address.getNeighborhood(),
                address.getComplement(),
                address.getState(),
                address.getCity());
        return dto;
    }
}
