package com.teamawsome.domain.rental;

import com.teamawsome.domain.book.Book;
import com.teamawsome.domain.member.Member;

import java.time.LocalDate;

public class Rental {

    public static final int DEFAULT_RENTING_TIME = 21;

    private int rentalId;
    private Book book;
    private Member member;
    private LocalDate startingDate;

    public Rental(int rentalId, Member member, Book book) {
        this.rentalId = rentalId;
        this.member = member;
        this.book = book;
        startingDate = LocalDate.now();
    }

    public Book getBook() {
        return book;
    }

    public Member getMember() {
        return member;
    }

    public LocalDate getStartingDate() {
        return startingDate;
    }

    public int getRentalId() {
        return rentalId;
    }

    public LocalDate getReturnDate(){
        return startingDate.plusDays(DEFAULT_RENTING_TIME);
    }
}
