package bnext.backend.api.reservation;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

// Crud repository ci fornisce i metodi per lavorare con il database, come primo argomento devo passare il tipo dei dati con cui
//dovrà lavorare(Reservation), come secondo argomento il tipo dell'id che questa classe ha(UUID ==> Id visibile nella classe Reservation.java)
public interface ReservationRepository extends CrudRepository<Reservation, UUID> {


}
