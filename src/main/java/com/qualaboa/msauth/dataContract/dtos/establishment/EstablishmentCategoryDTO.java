package com.qualaboa.msauth.dataContract.dtos.establishment;

import com.qualaboa.msauth.dataContract.entities.CategoryEmbeddedId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstablishmentCategoryDTO {
    private UUID establishmentId;
    private List<CategoryEmbeddedId> categories;
}
