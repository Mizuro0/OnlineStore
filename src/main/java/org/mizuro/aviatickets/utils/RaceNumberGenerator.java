package org.mizuro.aviatickets.utils;

public class RaceNumberGenerator {

    private final String[] symbols = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
            "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "-", "_", "a", "b", "c", "d",
            "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

    public static String generate() {
        RaceNumberGenerator raceNumberGenerator = new RaceNumberGenerator();
        StringBuilder raceNumber = new StringBuilder();
        for (int i = 0; i < 18; i++) {
            raceNumber.append(raceNumberGenerator.symbols[(int) (Math.random() * raceNumberGenerator.symbols.length)]);
        }
        return raceNumber.toString();
    }
}
