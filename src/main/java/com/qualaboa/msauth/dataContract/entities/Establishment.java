package com.qualaboa.msauth.dataContract.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    @Column(unique = true)
    private String cnpj;
    private Integer averageOrderValue;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @ManyToMany
    private List<Category> categories = new ArrayList<>();
}
