package com.teamawsome.api;

import com.teamawsome.api.book.BookController;
import com.teamawsome.api.book.BookDto;
import com.teamawsome.api.book.BookMapper;
import com.teamawsome.domain.book.Author;
import com.teamawsome.domain.book.Book;
import com.teamawsome.domain.book.BookRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookControllerTest {

    @Test
    void getAllBooks() {
        Author dries = new Author("dries", "bodaer");
        Book book1 = new Book(dries, "1354", "booktitle", "summary");
        Book book2= new Book(dries, "1234567890", "An aweful book", "Very aweful book");

        BookMapper bookMapper = new BookMapper();
        BookRepository bookRepository = new BookRepository();
        BookController bookController = new BookController(bookRepository, bookMapper);
        BookDto book1Dto = new BookDto(book1.getAuthor(), book1.getISBN(), book1.getTitle(), book1.getSummary());
        BookDto book2Dto = new BookDto(book2.getAuthor(), book2.getISBN(), book2.getTitle(), book2.getSummary());


        bookRepository.addBook(book1);
        bookRepository.addBook(book2);

        Assertions.assertThat(bookController.getAllBooks()).containsExactlyInAnyOrder(book1Dto , book2Dto);


    }
}