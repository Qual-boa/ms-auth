package com.qualaboa.msauth.dataContract.entities;

import com.qualaboa.msauth.dataContract.enums.CategoryTypeEnum;
import com.qualaboa.msauth.dataContract.enums.InteractionTypeEnum;
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
public class CategoryEmbeddedId  implements Serializable {
    private UUID establishmentId;
    private CategoryTypeEnum categoryType;
}
