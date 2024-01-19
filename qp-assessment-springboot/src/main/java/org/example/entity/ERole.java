package org.example.entity;

public enum ERole {
    ROLE_USER("ROLE_USER"),
    ROLE_ADMIN("ROLE_ADMIN");

    private final String value;

    private ERole(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
