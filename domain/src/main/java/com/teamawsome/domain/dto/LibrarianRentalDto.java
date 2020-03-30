package com.teamawsome.domain.dto;

import com.teamawsome.domain.book.Book;
import com.teamawsome.domain.member.Member;
import com.teamawsome.domain.rental.Rental;

import java.time.LocalDate;
import java.util.Objects;

public class LibrarianRentalDto {

    private int rentalId;
    private Book book;
    private Member member;
    private LocalDate startingDate;
    private LocalDate returnDate;

    public LibrarianRentalDto(int rentalId, Book book, Member member, LocalDate startingDate, LocalDate returnDate){
        this.rentalId = rentalId;
        this.book = book;
        this.member = member;
        this.startingDate = startingDate;
        this.returnDate = returnDate;
    }

    public static LibrarianRentalDto fromRental(Rental rental){
        return new LibrarianRentalDto(rental.getRentalId(),rental.getBook(),rental.getMember(),rental.getStartingDate(),rental.getReturnDate());
    }

    public int getRentalId(){
        return rentalId;
    }
    public Book getBook(){
        return book;
    }
    public Member getMember(){
        return member;
    }
    public LocalDate getStartingDate(){
        return startingDate;
    }
    public LocalDate getReturnDate(){
        return returnDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LibrarianRentalDto rentalDto = (LibrarianRentalDto) o;
        return rentalId == rentalDto.rentalId &&
                Objects.equals(book, rentalDto.book) &&
                Objects.equals(member, rentalDto.member) &&
                Objects.equals(startingDate, rentalDto.startingDate) &&
                Objects.equals(returnDate, rentalDto.returnDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rentalId, book, member, startingDate, returnDate);
    }
}
