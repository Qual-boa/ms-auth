package com.qualaboa.msauth.dataContract.dtos.establishment;

import com.qualaboa.msauth.dataContract.dtos.information.InformationResponseDTO;
import com.qualaboa.msauth.dataContract.dtos.relationship.RelationshipResponseDTO;
import lombok.*;

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
    List<RelationshipResponseDTO> relationships;
    InformationResponseDTO information;
}

