package com.teamawsome.domain.service;

import com.teamawsome.domain.book.Book;
import com.teamawsome.domain.book.BookNotPresentException;
import com.teamawsome.domain.book.BookRepository;
import com.teamawsome.domain.dto.*;
import com.teamawsome.domain.member.Member;
import com.teamawsome.domain.member.MemberRepository;
import com.teamawsome.domain.rental.Rental;
import com.teamawsome.domain.rental.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
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

    public List<RentalDto> getAllOverDueRentals(){
        return rentalRepository.getAllOverDueRentals().stream()
                .map(rentalMapper::toRentalDto)
                .collect(Collectors.toUnmodifiableList());
    }

    public ReturnedDto getReturn(Integer rentalId){
        if(rentalId == null) throw new IllegalArgumentException();

        Rental returnedRental = rentalRepository.returnRentedBook(rentalId);
        String lateMessage;
        if(returnedRental.getReturnDate().isBefore(LocalDate.now())){
            lateMessage="You are late!";
        }
        else{
            lateMessage="You're on time!";
        }

        return new ReturnedDto(returnedRental.getRentalId(), returnedRental.getMember().getId(), returnedRental.getBook().getISBN(), lateMessage);
    }

    public RentalDto rentBook(RentBookDto rentBookDto){
        Book book;
        book = bookRepository.getBook(rentBookDto.getIsbn());
        rentalRepository.isAvailable(book);
        Member member;
        member = memberRepository.getMemberById(rentBookDto.getMemberId());
        Rental newRental = rentalRepository.add(member, book);
        return new RentalDto(newRental.getRentalId(), newRental.getMember().getId(), rentBookDto.getIsbn(), newRental.getReturnDate());
    }

}
