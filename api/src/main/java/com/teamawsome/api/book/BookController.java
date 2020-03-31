package com.teamawsome.api.book;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.teamawsome.domain.book.*;
import com.teamawsome.domain.dto.BookAddedDto;
import com.teamawsome.domain.dto.BookDto;
import com.teamawsome.domain.service.BookMapper;
import com.teamawsome.domain.dto.FindByAuthorDto;
import com.teamawsome.domain.service.LibraryManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/books")
public class BookController {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final LibraryManagement libraryManagement;

    @Autowired
    public BookController(BookRepository bookRepository, BookMapper bookMapper, LibraryManagement libraryManagement) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
        this.libraryManagement = libraryManagement;
    }

    @GetMapping(produces = "application/json")
    public List<BookDto> getAllBooks() {
        return libraryManagement.getAllBooks();
    }

    @GetMapping(produces = "application/json", consumes = "application/json", path = "/{ISBN}")
    public BookDto getDetailsOfBook(@PathVariable("ISBN") String isbn) {
        return libraryManagement.getDetailsOfBook(isbn);
    }

    @GetMapping(produces = "application/json;charset=UTF-8",params = {"withIsbn"})
    public List<BookDto> searchByWildCardISBN(@RequestParam("withIsbn") String wildCard) {
        return libraryManagement.findByISBN(wildCard);
    }

    @GetMapping(produces = "application/json;charset=UTF-8",params = {"withAuthor"})
    public List<BookDto> searchByWildCardAuthor(@RequestParam(value = "withAuthor",required = true) String wildCard) throws JsonProcessingException {
        FindByAuthorDto author = new ObjectMapper().readValue(wildCard, FindByAuthorDto.class);
        return libraryManagement.findByAuthorName(author);
    }

    @GetMapping(produces = "application/json;charset=UTF-8",params = {"withTitle"})
    public List<BookDto> searchByWildCardTitle(@RequestParam("withTitle") String wildCard) {
        return libraryManagement.findByTitle(wildCard);
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    @PreAuthorize("hasAuthority('MAKE_LIBRARIAN')")
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto addBookStory10(@RequestBody BookAddedDto bookToAdd){
        return libraryManagement.addBook(bookToAdd);
    }

    @PutMapping(produces = "application/json", consumes = "application/json")
    @PreAuthorize("hasAuthority('MAKE_LIBRARIAN')")
    @ResponseStatus(HttpStatus.OK)
    public BookDto modifyBook(@RequestBody BookAddedDto bookToChange){
         return libraryManagement.changeBook(bookToChange);
    }

    @PreAuthorize("hasAuthority('MAKE_LIBRARIAN')")
    @PutMapping(path="/delete/{isbn}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBook(@PathVariable("isbn") String isbn){
        libraryManagement.deleteBook(isbn);
    }

    @PreAuthorize("hasAuthority('MAKE_LIBRARIAN')")
    @PutMapping(path="/restore/{isbn}")
    @ResponseStatus(HttpStatus.OK)
    public void restoreBook(@PathVariable("isbn") String isbn){
        libraryManagement.restoreBook(isbn);
    }


}
