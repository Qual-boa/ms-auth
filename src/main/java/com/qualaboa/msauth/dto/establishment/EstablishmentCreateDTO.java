package com.qualaboa.msauth.dto.establishment;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CNPJ;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstablishmentCreateDTO {

    @NotBlank
    private String fantasyName;

    @NotBlank
    @CNPJ
    private String cnpj;

    @NotNull
    @Min(value = 0, message = "Average Order Value can't be negative")
    private Integer averageOrderValue;
}
