package org.mizuro.aviatickets.utils;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.mizuro.aviatickets.entity.TicketEntity;
import org.mizuro.aviatickets.entity.UserEntity;
import org.mizuro.aviatickets.services.UserService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
public class TicketActuator {
    private static UserService userService;
    private static EntityManager entityManager;


    public static void checkActual() {
        entityManager.clear();
        List<TicketEntity> userTickets = userService.getCurrentUser().getTickets();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        for(TicketEntity ticketEntity : userTickets) {
            Date date = null;
            try {
                date = format.parse(ticketEntity.getDateToIssue());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            if(date.after(new Date())) {
                ticketEntity.setActual(false);
            }
        }
    }
}
