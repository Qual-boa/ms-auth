package com.qualaboa.msauth.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "establishments")
public class Establishment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String fantasyName;
    private String cnpj;
    private Integer averageOrderValue;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
