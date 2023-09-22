package org.matveyvs;

import lombok.extern.slf4j.Slf4j;
import org.matveyvs.dao.TicketDao;
import org.matveyvs.entity.Ticket;

@Slf4j
public class Main {
    public static void main(String[] args) {
        TicketDao ticketDao = TicketDao.getInstance();
        var ticket = getObject();
        Ticket save = ticketDao.save(ticket);
        log.warn("Ticket was saved: {}", save );
    }
    private static Ticket getObject() {
        Ticket build = Ticket.builder()
                .passportNo("test")
                .passengerName("Roman")
                .flightId(1L)
                .seatNo("test")
                .cost(22.22)
                .build();
        log.warn("User was created: {}", build);
        return build;
    }
}