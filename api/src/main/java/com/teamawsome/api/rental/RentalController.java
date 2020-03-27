package com.teamawsome.api.rental;

import com.teamawsome.domain.book.Book;
import com.teamawsome.domain.book.BookNotPresentException;
import com.teamawsome.domain.book.BookRepository;
import com.teamawsome.domain.member.MemberRepository;
import com.teamawsome.domain.rental.BookRentedOutException;
import com.teamawsome.domain.rental.RentalRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

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
        rentalRepository.add(rentBookDto.getMemberId(), book);
        return new RentalDto(1, 1, rentBookDto.getIsbn());
    }
}
