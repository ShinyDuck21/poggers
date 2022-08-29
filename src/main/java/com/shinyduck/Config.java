package com.shinyduck;

public class Config {
    private static String token = System.getenv("TOKEN");

    public static String getToken() {
        return token;
    }
}
