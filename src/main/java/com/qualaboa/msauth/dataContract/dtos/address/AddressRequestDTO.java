package com.qualaboa.msauth.dataContract.dtos.address;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequestDTO {

    @NotBlank
    private String street;

    @NotBlank
    private String number;

    @NotBlank
    private String postalCode;

    @NotBlank
    private String neighborhood;

    @NotBlank
    private String complement;

    @NotBlank
    private String city;

    @NotBlank
    private String state;
}
