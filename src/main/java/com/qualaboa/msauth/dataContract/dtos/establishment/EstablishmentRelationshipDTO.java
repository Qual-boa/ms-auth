package com.qualaboa.msauth.dataContract.dtos.establishment;

import com.qualaboa.msauth.dataContract.enums.InteractionTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstablishmentRelationshipDTO {
    private UUID establishmentId;
    private UUID userId;
    private InteractionTypeEnum interactionType;
    private String message;
    private Double rate;
}
