package com.teamawsome.api.book;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.teamawsome.domain.book.*;
import com.teamawsome.domain.service.Library;
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

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final Library library;

    @Autowired
    public BookController(BookRepository bookRepository, BookMapper bookMapper, Library library) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
        this.library = library;
    }

    @GetMapping(produces = "application/json")
    public List<BookDto> getAllBooks() {
        List<Book> books = bookRepository.getAllBooks();
        return books.stream()
                .map(bookMapper::transformBookToBookDto)
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

    @PostMapping(produces = "application/json", consumes = "application/json")
    @PreAuthorize("hasAuthority('MAKE_LIBRARIAN')")
    @ResponseStatus(HttpStatus.CREATED)
    public BookAddedDto addBookStory10(@RequestBody BookAddedDto bookAddedDto){
        bookRepository.addBook(bookMapper.transformBookAddedDtoBook(bookAddedDto));
        return bookAddedDto;
    }

    @PutMapping(produces = "application/json", consumes = "application/json")
    @PreAuthorize("hasAuthority('MAKE_LIBRARIAN')")
    @ResponseStatus(HttpStatus.OK)
    public BookDto modifyBook(@RequestBody BookAddedDto bookAddedDto){
         Book toReturn = bookRepository.changeBook(bookAddedDto.isbn,bookAddedDto.firstName,bookAddedDto.lastName,bookAddedDto.summary,bookAddedDto.title);
         return bookMapper.transformBookToBookDto(toReturn);
    }

    @PreAuthorize("hasAuthority('LIBRARIAN')")
    @PutMapping(path="/delete/{isbn}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBook(@PathVariable("isbn") String isbn){
        library.deleteBook(isbn);
    }

    @PreAuthorize("hasAuthority('LIBRARIAN')")
    @PutMapping(path="/restore/{isbn}")
    @ResponseStatus(HttpStatus.OK)
    public void restoreBook(@PathVariable("isbn") String isbn){
        library.restoreBook(isbn);
    }


}
