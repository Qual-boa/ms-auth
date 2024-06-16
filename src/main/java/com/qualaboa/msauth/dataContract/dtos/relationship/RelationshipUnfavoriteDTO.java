package com.qualaboa.msauth.dataContract.dtos.relationship;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelationshipUnfavoriteDTO {
    @NotNull
    private UUID userId;
    @NotNull
    private UUID establishmentId;
}
