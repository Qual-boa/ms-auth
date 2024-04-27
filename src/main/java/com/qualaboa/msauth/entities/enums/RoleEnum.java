package com.qualaboa.msauth.entities.enums;

import lombok.Getter;

@Getter
public enum RoleEnum {
    ADMIN("admin"),
    EMPLOYEE("employee"),
    USER("user");

    final private String role;

    RoleEnum(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
