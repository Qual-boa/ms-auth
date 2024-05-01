package com.qualaboa.msauth.entities.enums;

public enum FoodEnum {
    HOMEMADE(1),
    BRAZILIAN(2),
    MEXICAN(3),
    FAST_FOOD(4),
    PUB_FOOD(5);

    private int id;

    FoodEnum(int id) {
        this.id = id;
    }
    public int getValue() { return id; }
}
