package com.library.model;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * An "advance booking" — a user reserves a book that is currently issued
 * to someone else, so they can be first in line once it's returned.
 */
@Entity
@Table(name = "reservations")
public class Reservation {

    public enum Status { ACTIVE, FULFILLED, CANCELLED }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private LocalDate reservedDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.ACTIVE;

    public Reservation() {}

    public Reservation(Book book, User user, LocalDate reservedDate) {
        this.book = book;
        this.user = user;
        this.reservedDate = reservedDate;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public LocalDate getReservedDate() { return reservedDate; }
    public void setReservedDate(LocalDate reservedDate) { this.reservedDate = reservedDate; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
}
