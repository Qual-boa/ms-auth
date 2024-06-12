package com.qualaboa.msauth.dataContract.dtos.establishment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstablishmentCreateDTO {

    @NotBlank
    private String fantasyName;

    @NotBlank
    private String cnpj;
    
    @NotNull
    private Integer averageOrderValue;
}
