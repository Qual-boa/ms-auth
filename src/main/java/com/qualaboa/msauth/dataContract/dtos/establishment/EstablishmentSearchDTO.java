package com.qualaboa.msauth.dataContract.dtos.establishment;

import com.qualaboa.msauth.dataContract.enums.DrinkEnum;
import com.qualaboa.msauth.dataContract.enums.FoodEnum;
import com.qualaboa.msauth.dataContract.enums.MusicEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EstablishmentSearchDTO {
    private String name;
    private List<MusicEnum> musics;
    private List<FoodEnum> foods;
    private List<DrinkEnum> drinks;
    private String sortOrder;
}
