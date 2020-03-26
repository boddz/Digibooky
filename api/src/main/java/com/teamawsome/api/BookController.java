package com.teamawsome.api;

import com.teamawsome.domain.book.Book;
import com.teamawsome.domain.book.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import java.awt.print.Book;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/books")
public class BookController {

    BookRepository bookRepository;
    BookMapper bookMapper;

    @Autowired
    public BookController(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @GetMapping(produces = "application/json")
    public List<BookDto> getAllBooks() {

        List<Book> books = bookRepository.getAllBooks();

        return books.stream()
                .map(book -> bookMapper.transformBookToBookDto(book))
                .collect(Collectors.toList());
    }

}
