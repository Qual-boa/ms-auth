package com.qualaboa.msauth.entities.enums;

public enum DrinkEnum {
    BEER(1),
    WINE(2),
    COCKTAILS(3);

    private int id;

    DrinkEnum(int id) {
        this.id = id;
    }
    public int getValue() { return id; }
}
