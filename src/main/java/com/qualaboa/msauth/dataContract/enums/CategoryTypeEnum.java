package com.qualaboa.msauth.dataContract.enums;

public enum CategoryTypeEnum {
    MUSIC_CATEGORIES(1),
    FOOD_CATEGORIES(2),
    DRINKS_CATEGORIES(3);

    private int id;

    CategoryTypeEnum(int id) {
        this.id = id;
    }
    public int getValue() { return id; }
}
