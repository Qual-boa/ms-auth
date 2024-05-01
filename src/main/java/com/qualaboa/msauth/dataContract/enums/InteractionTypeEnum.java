package com.qualaboa.msauth.dataContract.enums;

public enum InteractionTypeEnum {

    EMPLOYEE(1),
    FAVORITE(2),
    COMMENT(3),
    RATE(4);

    private int id;

    InteractionTypeEnum(int id) {
        this.id = id;
    }
    public int getValue() { return id; }
}
