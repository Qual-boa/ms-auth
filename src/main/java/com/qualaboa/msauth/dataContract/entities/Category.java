package com.qualaboa.msauth.dataContract.entities;


import com.qualaboa.msauth.dataContract.enums.CategoryTypeEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.type.ListType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "categories")
public class Category {
    @Id
    private CategoryEmbeddedId id;
    private String name;
    private Integer countSearches;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
