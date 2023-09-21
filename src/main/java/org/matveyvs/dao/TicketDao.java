package org.matveyvs.dao;

import org.hibernate.HibernateException;
import org.hibernate.cfg.Configuration;
import org.matveyvs.Ticket;


public class TicketDao {
    private static final TicketDao INSTANCE = new TicketDao();

    public Ticket save(Ticket ticket) {
        Configuration configuration = new Configuration();
        configuration.configure();
        try (var sessionFactory = configuration.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();
            Long objectId = (Long) session.save(Ticket.builder()
                    .passportNo(ticket.getPassportNo())
                    .passengerName(ticket.getPassengerName())
                    .flightId(ticket.getFlightId())
                    .seatNo(ticket.getSeatNo())
                    .cost(ticket.getCost())
                    .build());
            Ticket ticketFromDB = session.get(Ticket.class, objectId);
            session.getTransaction().commit();
            return ticketFromDB;
        }
    }

    public boolean update(Ticket ticket) {
        Configuration configuration = new Configuration();
        configuration.configure();
        try (var sessionFactory = configuration.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(ticket);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(Long id) {
        Configuration configuration = new Configuration();
        configuration.configure();
        try (var sessionFactory = configuration.buildSessionFactory();
             var session = sessionFactory.openSession()) {
            session.beginTransaction();
            Ticket ticket = session.get(Ticket.class, id);
            session.delete(ticket);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
            return false;
        }

    }

    private TicketDao() {

    }

    public static TicketDao getInstance() {
        return INSTANCE;
    }
}
