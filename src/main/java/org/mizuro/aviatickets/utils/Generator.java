package org.mizuro.aviatickets.utils;

public class Generator {

    private final String[] symbols = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
            "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "-", "_", "a", "b", "c", "d",
            "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

    public static String generateId() {
        Generator generator = new Generator();
        StringBuilder id = new StringBuilder();
        for (int i = 0; i < 18; i++) {
            id.append(generator.symbols[(int) (Math.random() * generator.symbols.length)]);
        }
        return id.toString();
    }

    public static String generateETKT() {
        Generator generator = new Generator();
        StringBuilder etkt = new StringBuilder();
        for (int i = 0; i < 13; i++) {
            etkt.append(generator.symbols[(int) (Math.random() * generator.symbols.length)]);
        }
        return etkt.toString();
    }

    public static String generateConfirmToken() {
        Generator generator = new Generator();
        StringBuilder token = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            token.append(generator.symbols[(int) (Math.random() * generator.symbols.length)]);
        }
        return token.toString();
    }
}
