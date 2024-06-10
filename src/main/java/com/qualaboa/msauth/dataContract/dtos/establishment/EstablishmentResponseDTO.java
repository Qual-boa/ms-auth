package com.qualaboa.msauth.dataContract.dtos.establishment;

import com.qualaboa.msauth.dataContract.entities.Category;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstablishmentResponseDTO {
    UUID id;
    String fantasyName;
    String cnpj;
    Integer averageOrderValue;
    List<CategoryResponseDTO> categories;
}

