package com.library.repository;

import com.library.model.Book;
import com.library.model.Reservation;
import com.library.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUserOrderByReservedDateDesc(User user);
    List<Reservation> findByBookAndStatusOrderByReservedDateAsc(Book book, Reservation.Status status);
    List<Reservation> findByStatus(Reservation.Status status);
}
