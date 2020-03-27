package com.teamawsome.api.rental;

import com.teamawsome.domain.book.Book;
import com.teamawsome.domain.book.BookNotPresentException;
import com.teamawsome.domain.book.BookRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class RentalController {

    BookRepository bookRepository;

    public RentalController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public RentalDto rentBook(RentBookDto rentBookDto) {
        Book bookToBeRented;
        try {
            bookToBeRented = bookRepository.getBook(rentBookDto.getIsbn());
        } catch (BookNotPresentException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        RentalDto rentalDto = new RentalDto(1,1, "123456");

        return rentalDto;
    }
}
