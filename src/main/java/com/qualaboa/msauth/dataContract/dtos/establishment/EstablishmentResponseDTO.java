package com.qualaboa.msauth.dataContract.dtos.establishment;

import java.time.LocalDateTime;
import java.util.UUID;

public record EstablishmentResponseDTO(
        UUID id,
        String fantasyName,
        String cnpj,
        Integer averageOrderValue,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
