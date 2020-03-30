package com.teamawsome.api.book;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.teamawsome.domain.book.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping(produces = "application/json", consumes = "application/json", path = "/{ISBN}")
    public BookDto getDetailsOfBook(@PathVariable String ISBN) {
        try {
            Book askedBook = bookRepository.getBook(ISBN);
            return bookMapper.transformBookToBookDto(askedBook);
        } catch (BookNotPresentException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Go away, no book today!", exception);
        }
    }

    @GetMapping(produces = "application/json;charset=UTF-8",params = {"withIsbn"})
    public List<BookDto> searchByWildCardISBN(@RequestParam("withIsbn") String wildCard) {
        return bookRepository.findByISBNWildCard(wildCard).stream()
                .map(bookMapper::transformBookToBookDto)
                .collect(Collectors.toUnmodifiableList());
    }

    @GetMapping(produces = "application/json;charset=UTF-8",params = {"withAuthor"})
    public List<BookDto> searchByWildCardAuthor(@RequestParam(value = "withAuthor",required = true) String wildCard) throws JsonProcessingException {
        FindByAuthorDto author = new ObjectMapper().readValue(wildCard, FindByAuthorDto.class);
        return bookRepository.findByAuthorName(author).stream()
                .map(bookMapper::transformBookToBookDto)
                .collect(Collectors.toUnmodifiableList());
    }

    @GetMapping(produces = "application/json;charset=UTF-8",params = {"withTitle"})
    public List<BookDto> searchByWildCardTitle(@RequestParam("withTitle") String wildCard) {
        return bookRepository.findByTitle(wildCard).stream()
                .map(bookMapper::transformBookToBookDto)
                .collect(Collectors.toUnmodifiableList());
    }

    /*@PostMapping(produces = "application/json", consumes = "application/json")
    public BookDto addBook(@RequestBody BookDto bookDto) {
        bookRepository.addBook(bookMapper.transformBookDtoToBook(bookDto));
        return bookDto;
    }*/

    @PostMapping(produces = "application/json", consumes = "application/json")
    public BookAddedDto addBookStory10(@RequestBody BookAddedDto bookAddedDto){
        //Book newBook=new Book(new Author(bookAddedDto.firstName, bookAddedDto.lastName), bookAddedDto.isbn, bookAddedDto.title, bookAddedDto.summary);
        bookRepository.addBook(bookMapper.transformBookAddedDtoBook(bookAddedDto));
        return bookAddedDto;
    }

    @PutMapping(produces = "application/json", consumes = "application/json")
    @PreAuthorize("hasAuthority('MAKE_LIBRARIAN')")
    public BookDto modifyBook(@RequestBody BookAddedDto bookAddedDto){
         Book toreturn = bookRepository.changeBook(bookAddedDto.isbn,bookAddedDto.firstName,bookAddedDto.lastName,bookAddedDto.summary,bookAddedDto.title);
         return bookMapper.transformBookToBookDto(toreturn);



    }
}
