package org.mizuro.aviatickets.utils;

public class Generator {

    private final String[] symbols = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
            "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "-", "_", "a", "b", "c", "d",
            "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

    public static String generateId() {
        Generator generator = new Generator();
        StringBuilder raceNumber = new StringBuilder();
        for (int i = 0; i < 18; i++) {
            raceNumber.append(generator.symbols[(int) (Math.random() * generator.symbols.length)]);
        }
        return raceNumber.toString();
    }

    public static String generateETKT() {
        Generator generator = new Generator();
        StringBuilder raceNumber = new StringBuilder();
        for (int i = 0; i < 13; i++) {
            raceNumber.append(generator.symbols[(int) (Math.random() * generator.symbols.length)]);
        }
        return raceNumber.toString();
    }
}
