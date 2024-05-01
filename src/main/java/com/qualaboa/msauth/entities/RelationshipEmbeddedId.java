package com.qualaboa.msauth.entities;

import com.qualaboa.msauth.entities.enums.InteractionTypeEnum;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class RelationshipEmbeddedId implements Serializable {

    private UUID userId;
    private UUID establishmentId;
    private InteractionTypeEnum interactionType;
}
