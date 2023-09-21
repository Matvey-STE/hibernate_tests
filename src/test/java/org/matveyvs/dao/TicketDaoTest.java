package org.matveyvs.dao;

import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.Test;
import org.matveyvs.Ticket;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TicketDaoTest {
    private final TicketDao ticketDao = TicketDao.getInstance();

    private static Ticket getObject() {
        return Ticket.builder()
                .passportNo("test")
                .passengerName("Roman")
                .flightId(1L)
                .seatNo("test")
                .cost(22.22)
                .build();
    }

    @Test
    void save() {
        Ticket test = getObject();

        Ticket saved = ticketDao.save(test);

        assertNotNull(saved);
        assertEquals(test.getPassengerName(), saved.getPassengerName());
        assertEquals(test.getCost(), saved.getCost());
        assertEquals(test.getSeatNo(), saved.getSeatNo());

        ticketDao.delete(saved.getId());
    }

    @Test
    void update() {
        Ticket saved = ticketDao.save(getObject());

        Ticket updatedObject = new Ticket(
                saved.getId(),
                "updatedTest",
                "updatedTest",
                1L,
                "uptt",
                33.33);
        boolean updated = ticketDao.update(updatedObject);
        assertTrue(updated);

        Optional<Ticket> ticket;
        Configuration configuration = new Configuration();
        configuration.configure();
        try (var sessionFactory = configuration.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();
            ticket = Optional.ofNullable(session.find(Ticket.class, saved.getId()));
            session.getTransaction().commit();
        }
        assertTrue(ticket.isPresent());
        assertEquals(updatedObject.getId(), ticket.get().getId());
        assertEquals(updatedObject.getCost(), ticket.get().getCost());
        ticketDao.delete(saved.getId());
    }

    @Test
    void delete() {
        Ticket test = getObject();

        Ticket saved = ticketDao.save(test);

        boolean deleted = ticketDao.delete(saved.getId());
        assertTrue(deleted);
        Optional<Ticket> deletedObject;
        Configuration configuration = new Configuration();
        configuration.configure();
        try (var sessionFactory = configuration.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();
            deletedObject = Optional.ofNullable(session.find(Ticket.class, saved.getId()));
            session.getTransaction().commit();
        }
        assertTrue(deletedObject.isEmpty());
        if (!deletedObject.isEmpty()) {
            ticketDao.delete(saved.getId());
        }
    }
}