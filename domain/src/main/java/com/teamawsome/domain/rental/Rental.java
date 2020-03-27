package com.teamawsome.domain.rental;

import com.teamawsome.domain.book.Book;
import com.teamawsome.domain.member.Member;

import java.time.LocalDate;

public class Rental {

    private Book book;
    private Member member;
    private LocalDate startingDate;

    public Rental(Book book) {
        this.book = book;
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
}
