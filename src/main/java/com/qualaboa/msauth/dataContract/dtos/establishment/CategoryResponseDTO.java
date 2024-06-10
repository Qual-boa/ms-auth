package com.qualaboa.msauth.dataContract.dtos.establishment;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class CategoryResponseDTO {
    private Integer categoryType;
    private Integer category;
    private String name;
}
