package org.matveyvs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ticket", schema = "public")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "passport_no")
    String passportNo;
    @Column(name = "passenger_name")
    String passengerName;
    @Column(name = "flight_id")
    Long flightId;
    @Column(name = "seat_no")
    String seatNo;
    Double cost;
}
