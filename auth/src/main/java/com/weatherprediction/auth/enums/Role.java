package com.weatherprediction.auth.enums;

public enum Role {
    ADMIN(Constants.ADMIN),
    USER(Constants.USER);

    private final String value;

    Role(String value) {
        try {
            if (!value.equals(Constants.class.getField(this.name()).get(null))) {
                throw new IllegalArgumentException(value);
            }
            this.value = value;
        } catch (NoSuchFieldException | IllegalAccessException exception) {
            throw new IllegalArgumentException(this.name());
        }
    }

    public String getValue() {
        return this.value;
    }

    public static class Constants {
        public static final String ADMIN = "ADMIN";
        public static final String USER = "USER";
    }
}