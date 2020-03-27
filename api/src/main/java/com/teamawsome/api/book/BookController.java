package com.teamawsome.api.book;

import com.teamawsome.domain.book.Book;
import com.teamawsome.domain.book.BookNotPresentException;
import com.teamawsome.domain.book.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    @GetMapping(produces = "application/json", consumes = "application/json", path = "{ISBN}")
    public BookDto getDetailsOfBook(@PathVariable String ISBN) {
        try {
            Book askedBook = bookRepository.getBook(ISBN);
            return bookMapper.transformBookToBookDto(askedBook);
        } catch (BookNotPresentException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Go away, no book today!", exception);
        }
    }

    @GetMapping(produces = "application/json;charset=UTF-8", path = "/isbn")
    public List<BookDto> searchByWildCardISBN(@RequestParam("wildcard") String wildCard) {
        return bookRepository.findByISBNWildCard(wildCard).stream()
                .map(bookMapper::transformBookToBookDto)
                .collect(Collectors.toUnmodifiableList());
    }



}
