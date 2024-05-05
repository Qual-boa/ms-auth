package com.qualaboa.msauth.dataContract.dtos.establishment;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstablishmentUpdateDTO {
    @NotNull
    @Id
    private UUID id;
    @NotBlank
    private String fantasyName;
}
