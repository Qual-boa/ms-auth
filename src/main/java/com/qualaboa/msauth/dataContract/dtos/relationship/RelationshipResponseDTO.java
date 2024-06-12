package com.qualaboa.msauth.dataContract.dtos.relationship;

import com.qualaboa.msauth.dataContract.enums.InteractionTypeEnum;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Data
public class RelationshipResponseDTO {
    private UUID userId;
    private UUID establishmentId;
    private InteractionTypeEnum interactionType;
    private Double rate;
    private String message;
}
