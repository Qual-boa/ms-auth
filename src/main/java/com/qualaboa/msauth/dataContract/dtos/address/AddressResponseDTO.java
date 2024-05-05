package com.qualaboa.msauth.dataContract.dtos.address;

import java.util.UUID;

public record AddressResponseDTO(
        UUID id,
        String street,
        String number,
        String postalCode,
        String neighborhood,
        String complement,
        String state,
        String city
) {
}
