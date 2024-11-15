package com.qualaboa.msauth.dataContract.dtos.establishment;

import com.qualaboa.msauth.dataContract.entities.CategoryEmbeddedId;
import com.qualaboa.msauth.dataContract.enums.SortOrderEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EstablishmentSearchDTO {
    private String name;
    private List<CategoryEmbeddedId> categories;
    private SortOrderEnum sortOrder;
}
