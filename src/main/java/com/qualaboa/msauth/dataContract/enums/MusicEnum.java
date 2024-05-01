package com.qualaboa.msauth.dataContract.enums;

public enum MusicEnum {
    LIVE(1),
    ROCK(2),
    COUNTRY(3),
    BRAZILIAN_COUNTRY(4);

    private int id;

    MusicEnum(int id) {
        this.id = id;
    }
    public int getValue() { return id; }
}
