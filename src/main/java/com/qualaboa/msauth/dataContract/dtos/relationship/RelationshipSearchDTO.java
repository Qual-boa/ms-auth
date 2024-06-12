package com.qualaboa.msauth.dataContract.dtos.relationship;

import com.qualaboa.msauth.dataContract.enums.InteractionTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelationshipSearchDTO {
    private UUID establishmentId;
    private InteractionTypeEnum interactionType;
}
