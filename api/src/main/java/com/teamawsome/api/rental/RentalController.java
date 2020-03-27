package com.teamawsome.api.rental;

import com.teamawsome.domain.book.Book;
import com.teamawsome.domain.book.BookNotPresentException;
import com.teamawsome.domain.book.BookRepository;
import com.teamawsome.domain.member.Member;
import com.teamawsome.domain.member.MemberRepository;
import com.teamawsome.domain.rental.BookRentedOutException;
import com.teamawsome.domain.rental.Rental;
import com.teamawsome.domain.rental.RentalRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;

public class RentalController {

    BookRepository bookRepository;
    RentalRepository rentalRepository;
    MemberRepository memberRepository;

    public RentalController(BookRepository bookRepository, RentalRepository rentalRepository, MemberRepository memberRepository) {
        this.bookRepository = bookRepository;
        this.rentalRepository = rentalRepository;
        this.memberRepository = memberRepository;
    }

    public RentalDto rentBook(RentBookDto rentBookDto) {
        Book book;
        try {
            book = bookRepository.getBook(rentBookDto.getIsbn());
            rentalRepository.isAvailable(book);
        } catch (BookNotPresentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book does not exist.");
        } catch (BookRentedOutException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book already rented out.");
        }

        Member member;
        try{
             member = memberRepository.getMemberById(rentBookDto.getMemberId());
        } catch (IllegalArgumentException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        Rental newRental = rentalRepository.add(member, book);
        return new RentalDto(newRental.getRentalId(), newRental.getMember().getId(), rentBookDto.getIsbn(), newRental.getReturnDate());
    }
}
