package com.qualaboa.msauth.dataContract.entities;

import jakarta.persistence.*;
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
@Entity
@Table(name = "informations")
public class Information {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID establishmentId;
}
