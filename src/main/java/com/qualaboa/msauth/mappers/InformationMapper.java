package com.qualaboa.msauth.mappers;

import com.qualaboa.msauth.dataContract.dtos.information.InformationCreateDTO;
import com.qualaboa.msauth.dataContract.dtos.information.InformationResponseDTO;
import com.qualaboa.msauth.dataContract.entities.Establishment;
import com.qualaboa.msauth.dataContract.entities.Information;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class InformationMapper implements IMapper<Information> {

    @Override
    public Information toEntity(Object obj) {
        return null;
    }

    @Override
    public Object toDto(Information information) {
        if(information == null) return null;
        InformationResponseDTO dto = new InformationResponseDTO();
        dto.setOwner(information.getOwner());
        dto.setId(information.getId());
        dto.setHasParking(information.getHasParking());
        dto.setHasAccessibility(information.getHasAccessibility());
        dto.setHasTv(information.getHasTv());
        dto.setHasWifi(information.getHasWifi());
        dto.setPhone(information.getPhone());
        dto.setCloseAt(information.getCloseAt());
        dto.setOpenAt(information.getOpenAt());
        dto.setFacebookUrl(information.getFacebookUrl());
        dto.setInstagramUrl(information.getInstagramUrl());
        dto.setTelegramUrl(information.getTelegramUrl());
        dto.setUpdatedAt(dto.getUpdatedAt());
        dto.setCreatedAt(dto.getCreatedAt());
        return dto;
    }

    public Information toEntity(Establishment owner, InformationCreateDTO createDTO) {
        if(createDTO == null) return null;
        Information info = new Information();
        info.setHasParking(createDTO.getHasParking());
        info.setHasAccessibility(createDTO.getHasAccessibility());
        info.setHasTv(createDTO.getHasTv());
        info.setHasWifi(createDTO.getHasWifi());
        info.setCloseAt(LocalTime.of(createDTO.getCloseAt().getHour(), createDTO.getCloseAt().getMinute()));
        info.setOpenAt(LocalTime.of(createDTO.getOpenAt().getHour(), createDTO.getOpenAt().getMinute()));
        info.setPhone(createDTO.getPhone());
        info.setFacebookUrl(createDTO.getFacebookUrl());
        info.setInstagramUrl(createDTO.getInstagramUrl());
        info.setTelegramUrl(info.getTelegramUrl());
        info.setOwner(owner);
        return info;
    }

}
