package com.teamawsome.api.rental;

import com.teamawsome.domain.book.Book;
import com.teamawsome.domain.book.BookNotPresentException;
import com.teamawsome.domain.book.BookRepository;
import com.teamawsome.domain.member.Member;
import com.teamawsome.domain.member.MemberRepository;
import com.teamawsome.domain.rental.BookRentedOutException;
import com.teamawsome.domain.rental.Rental;
import com.teamawsome.domain.rental.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RestController
@RequestMapping("rentals")
public class RentalController {

    BookRepository bookRepository;
    RentalRepository rentalRepository;
    MemberRepository memberRepository;

    @Autowired
    public RentalController(BookRepository bookRepository, RentalRepository rentalRepository, MemberRepository memberRepository) {
        this.bookRepository = bookRepository;
        this.rentalRepository = rentalRepository;
        this.memberRepository = memberRepository;
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    public RentalDto rentBook(@RequestBody RentBookDto rentBookDto) {
        Book book;
            book = bookRepository.getBook(rentBookDto.getIsbn());
            rentalRepository.isAvailable(book);
        Member member;
            member = memberRepository.getMemberById(rentBookDto.getMemberId());
        Rental newRental = rentalRepository.add(member, book);
        return new RentalDto(newRental.getRentalId(), newRental.getMember().getId(), rentBookDto.getIsbn(), newRental.getReturnDate());
    }


    /*
    Exception handlers
     */
    @ExceptionHandler(BookNotPresentException.class)
    protected void bookNotPresentException(BookNotPresentException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler(BookRentedOutException.class)
    protected void bookRentedOutException(BookRentedOutException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected void illegalArgumentException(IllegalArgumentException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
}
