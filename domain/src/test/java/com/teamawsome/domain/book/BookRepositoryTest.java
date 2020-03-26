package com.teamawsome.domain.book;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookRepositoryTest {

        @Test
        void getAllBooks_returnsListOfAllBooks() {
            //given
            BookRepository bookRepository = new BookRepository();
            Author dries= new Author("Dries", "Bodaer");
            Book book1 = new Book(dries, "1234567890", "An excellent book", "Very interesting book");
            bookRepository.addBook(book1);

            Book book2= new Book(dries, "1234567890", "An aweful book", "Very aweful book");
            bookRepository.addBook(book2);

            //when
            List<Book> listOfBook=bookRepository.getAllBooks();

            //then
            Assertions.assertThat(listOfBook).containsExactlyInAnyOrder(book1, book2);
    }

}