package org.mizuro.aviatickets;


import org.junit.jupiter.api.Test;
import org.mizuro.aviatickets.models.Seat;

public class SeatsTest {

    @Test
    void test() {
        Seat[][] seats = Seat.createSeats(6, 21);
        for (int i = 0; i < 21; i++) {
            for (int j = 0; j < 6; j++) {
                System.out.print(seats[j][i].getSeat() + " ");
            }
            System.out.println();
        }
    }
}
