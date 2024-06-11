package com.qualaboa.msauth.dataContract.dtos.information;


import com.qualaboa.msauth.dataContract.entities.Establishment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InformationResponseDTO {

    private UUID id;
    private Boolean hasParking;
    private Boolean hasAccessibility;
    private Boolean hasTv;
    private Boolean hasWifi;
    private LocalTime openAt;
    private LocalTime closeAt;
    private String phone;
    private String facebookUrl;
    private String instagramUrl;
    private String telegramUrl;
}
