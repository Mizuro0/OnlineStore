package org.mizuro.aviatickets.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Seat {
    private int id;
    private String seat;
    private boolean actual;

    public static Seat[][] createSeats(int columnCount, int rowsCount) {
        Seat[][] seats = new Seat[columnCount][rowsCount];
        String[] seatNumbers = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21"};
        String[] rowNumbers = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        int counter = 0;
        for (int i = 0; i < columnCount; i++) {
            for (int j = 0; j < rowsCount; j++) {
                seats[i][j] = new Seat(counter, rowNumbers[i] + seatNumbers[j], true);
                counter++;
            }
        }
        return seats;
    }

    public static Seat getById(int id, Seat[][] seats) {
        for (Seat[] value : seats) {
            for (Seat item : value) {
                if (item.getId() == id) {
                    return item;
                }
            }
        }
        return null;

    }
}
