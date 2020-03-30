package com.teamawsome.api.rental;

import com.teamawsome.domain.book.Book;
import com.teamawsome.domain.book.BookNotPresentException;
import com.teamawsome.domain.book.BookRepository;
import com.teamawsome.domain.dto.RentBookDto;
import com.teamawsome.domain.dto.RentalDto;
import com.teamawsome.domain.dto.ReturnedDto;
import com.teamawsome.domain.member.Member;
import com.teamawsome.domain.member.MemberRepository;
import com.teamawsome.domain.rental.BookRentedOutException;
import com.teamawsome.domain.dto.LibrarianRentalDto;
import com.teamawsome.domain.rental.Rental;
import com.teamawsome.domain.rental.RentalRepository;
import com.teamawsome.domain.service.Library;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("rentals")
public class RentalController {

    BookRepository bookRepository;
    RentalRepository rentalRepository;
    MemberRepository memberRepository;
    Library library;

    @Autowired
    public RentalController(BookRepository bookRepository, RentalRepository rentalRepository, MemberRepository memberRepository, Library library) {
        this.bookRepository = bookRepository;
        this.rentalRepository = rentalRepository;
        this.memberRepository = memberRepository;
        this.library = library;
    }

    @PreAuthorize("hasAuthority('LIBRARIAN')")
    @GetMapping(produces = "application/json;charset=UTF-8", params = {"withInss"})
    public List<LibrarianRentalDto> findBooksLentToMemberIdentifiedByINSS(@RequestParam("withInss") String inss){
        return library.findRentalsByMember(inss);
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    public RentalDto rentBook(@RequestBody RentBookDto rentBookDto) {
        Book book;
        book = bookRepository.getBook(rentBookDto.getIsbn());
        rentalRepository.isAvailable(book);
        Member member;
        member = memberRepository.getMemberById(rentBookDto.getMemberId());
        Rental newRental = rentalRepository.add(member, book);
        return new RentalDto(newRental.getRentalId(), newRental.getMember().getId(), rentBookDto.getIsbn(), newRental.getReturnDate());
    }
    @GetMapping(produces = "application/json", path="/return/{rentalId}")
    public ReturnedDto returnBook(@PathVariable int rentalId){
        Rental returnedRental=rentalRepository.returnRentedBook(rentalId);
        String lateMessage;
        if(returnedRental.getReturnDate().compareTo(LocalDate.now())<0){
            lateMessage="You are late!";
        }
        else{
            lateMessage="You're on time!";
        }

        return new ReturnedDto(returnedRental.getRentalId(), returnedRental.getMember().getId(), returnedRental.getBook().getISBN(), lateMessage);
    }

    /*
    Exception handlers
     */
    @ExceptionHandler(BookNotPresentException.class)
    protected void bookNotPresentException(BookNotPresentException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler(BookRentedOutException.class)
    protected void bookRentedOutException(BookRentedOutException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected void illegalArgumentException(IllegalArgumentException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }


}
