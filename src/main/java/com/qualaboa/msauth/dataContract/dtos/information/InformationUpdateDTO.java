package com.qualaboa.msauth.dataContract.dtos.information;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InformationUpdateDTO {
    @NotNull
    private Boolean hasParking;
    @NotNull
    private Boolean hasAccessibility;
    @NotNull
    private Boolean hasTv;
    @NotNull
    private Boolean hasWifi;
    @NotNull
    private InformationTimeDTO openAt;
    @NotNull
    private InformationTimeDTO closeAt;
    @NotBlank
    @Pattern(regexp = "^\\\\d{2}\\\\d{8,9}$", message = "Phone isn't on pattern XXXXXXXXXXX, only numbers")
    private String phone;
    @NotBlank
    private String facebookUrl;
    @NotNull
    private String instagramUrl;
    @NotNull
    private String telegramUrl;
    @NotNull
    private UUID establishmentId;
    private String description;
}
