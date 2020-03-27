package com.teamawsome.api.rental;

import com.teamawsome.domain.book.Author;
import com.teamawsome.domain.book.Book;
import com.teamawsome.domain.book.BookRepository;
import com.teamawsome.domain.member.MemberRepository;
import com.teamawsome.domain.rental.RentalRepository;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RentalControllerTest {

    @Test
    void rentBook_ifGivenBookDoesNotExist_throwException() {
        //given
        RentalController rentalController = new RentalController(new BookRepository(), new RentalRepository(), new MemberRepository());
        RentBookDto rentBookDto = new RentBookDto("whatever", "whatever");
        //when
        //then
        assertThatThrownBy(() -> rentalController.rentBook(rentBookDto)).isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void rentBook_ifGivenBookExistsAndIsAvailable_returnsDetailsOfBookAndReturnDate() {
        //given
        BookRepository bookRepository = new BookRepository();
        RentalController rentalController = new RentalController(bookRepository, new RentalRepository(), new MemberRepository());
        Author author = new Author("Leo", "Tolstoj");
        Book book = new Book(author, "123456", "War and Peace", "It's a novel");
        bookRepository.addBook(book);

        //when
        RentBookDto bookToBeRentedDto = new RentBookDto("123456","member one");
        RentalDto actualRentalDto = rentalController.rentBook(bookToBeRentedDto);

        //then
        assertThat(actualRentalDto.getIsbn()).isEqualTo("123456");
    }


    @Test
    void rentBook_ifGivenBookExistsButIsNotAvailable_throwExceptionWithSuitableMessage() {
        //given
        BookRepository bookRepository = new BookRepository();
        RentalController rentalController = new RentalController(bookRepository, new RentalRepository(), new MemberRepository());
        Author author = new Author("Leo", "Tolstoj");
        Book book = new Book(author, "123456", "War and Peace", "It's a novel");
        bookRepository.addBook(book);
        RentBookDto bookToBeRentedDto = new RentBookDto("123456", "1");
        rentalController.rentBook(bookToBeRentedDto);

        //then
        assertThatThrownBy(() -> rentalController.rentBook(bookToBeRentedDto)).isInstanceOf(ResponseStatusException.class);
    }

}