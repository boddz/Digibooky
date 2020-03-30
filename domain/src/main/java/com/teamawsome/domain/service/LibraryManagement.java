package com.teamawsome.domain.service;

import com.teamawsome.domain.book.Book;
import com.teamawsome.domain.book.BookNotPresentException;
import com.teamawsome.domain.book.BookRepository;
import com.teamawsome.domain.dto.BookAddedDto;
import com.teamawsome.domain.dto.BookDto;
import com.teamawsome.domain.dto.FindByAuthorDto;
import com.teamawsome.domain.member.MemberRepository;
import com.teamawsome.domain.dto.LibrarianRentalDto;
import com.teamawsome.domain.rental.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibraryManagement {
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final RentalRepository rentalRepository;
    private final BookMapper bookMapper;
    private final RentalMapper rentalMapper;

    @Autowired
    public LibraryManagement(BookRepository bookRepository, MemberRepository memberRepository, RentalRepository rentalRepository,
                             BookMapper bookMapper, RentalMapper rentalMapper){
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
        this.rentalRepository = rentalRepository;
        this.bookMapper = bookMapper;
        this.rentalMapper = rentalMapper;
    }

    public List<LibrarianRentalDto> findRentalsByMember(final String inss){
        return rentalRepository.findOnCondition(rental -> rental.getMember().getInss().equals(inss))
                .stream().map(rentalMapper::toLibrarianRentalDto)
                .collect(Collectors.toUnmodifiableList());
    }

    public void deleteBook(String isbn){
        bookRepository.deleteBook(isbn);
    }

    public void restoreBook(String isbn){
        bookRepository.restoreBook(isbn);
    }

    public BookDto changeBook(BookAddedDto bookToChange){
        return bookMapper.toBookDto(bookRepository.changeBook(bookMapper.toBook(bookToChange)));
    }

    public List<BookDto> findByTitle(String wildCard){
        return bookRepository.findByTitle(wildCard).stream()
                .map(bookMapper::toBookDto)
                .collect(Collectors.toUnmodifiableList());
    }
    public List<BookDto> findByAuthorName(FindByAuthorDto author){
        return bookRepository.findByAuthorName(author).stream()
                .map(bookMapper::toBookDto)
                .collect(Collectors.toUnmodifiableList());
    }
    public List<BookDto> findByISBN(String wildCard){
        return bookRepository.findByISBNWildCard(wildCard).stream()
                .map(bookMapper::toBookDto)
                .collect(Collectors.toUnmodifiableList());
    }

    public BookDto getDetailsOfBook(String isbn){
        try {
            Book askedBook = bookRepository.getBook(isbn);
            return bookMapper.toBookDto(askedBook);
        } catch (BookNotPresentException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Go away, no book today!", exception);
        }

    }

    public List<BookDto> getAllBooks(){
        return bookRepository.getAllBooks().stream()
                .map(bookMapper::toBookDto)
                .collect(Collectors.toList());
    }

    public BookDto addBook(BookAddedDto toAdd){
        return bookMapper.toBookDto(bookRepository.addBook(bookMapper.toBook(toAdd)));
    }
}
