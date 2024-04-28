package com.qualaboa.msauth.entities.enums;

public enum InteractionType {

    EMPLOYEE(1), FAVORITE(2), COMMENT(3), RATE(4);

    private int id;

    InteractionType(int id) {
        this.id = id;
    }
    public int getValue() { return id; }
}
