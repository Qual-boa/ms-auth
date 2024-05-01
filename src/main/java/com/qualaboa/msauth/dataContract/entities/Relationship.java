package com.qualaboa.msauth.dataContract.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "relationships")
public class Relationship {

    @EmbeddedId
    private RelationshipEmbeddedId id;
    private Double rate;
    private String message;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
