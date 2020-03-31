package com.teamawsome.api.rental;

import com.teamawsome.api.book.BookController;
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
import com.teamawsome.domain.service.LibraryManagement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("rentals")
public class RentalController {
    private final LibraryManagement libraryManagement;
    private final BookRepository bookRepository;
    private final RentalRepository rentalRepository;
    private final MemberRepository memberRepository;
    private final Logger logger = LoggerFactory.getLogger(RentalController.class);


    @Autowired
    public RentalController(BookRepository bookRepository, RentalRepository rentalRepository, MemberRepository memberRepository, LibraryManagement libraryManagement) {
        this.bookRepository = bookRepository;
        this.rentalRepository = rentalRepository;
        this.memberRepository = memberRepository;
        this.libraryManagement = libraryManagement;

    }

    @PreAuthorize("hasAuthority('LIBRARIAN')")
    @GetMapping(produces = "application/json;charset=UTF-8", params = {"withInss"})
    public List<LibrarianRentalDto> findBooksLentToMemberIdentifiedByINSS(@RequestParam("withInss") String inss){
        return libraryManagement.findRentalsByMember(inss);
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    public RentalDto rentBook(@RequestBody RentBookDto rentBookDto) {
        return libraryManagement.rentBook(rentBookDto);
    }

    @GetMapping(produces = "application/json", path="/return/{rentalId}")
    public ReturnedDto returnBook(@PathVariable Integer rentalId){
        return libraryManagement.getReturn(rentalId);
    }

    @GetMapping(produces = "application/json", path="/overdue")
    @PreAuthorize("hasAuthority('LIBRARIAN')")
    public List<RentalDto> getListOfOverDueBooks(){
        return libraryManagement.getAllOverDueRentals();
    }

    /*
    Exception handlers
     */
    @ExceptionHandler(BookNotPresentException.class)
    protected void bookNotPresentException(BookNotPresentException e, HttpServletResponse response) throws IOException {
        logger.error(e.getMessage());
        response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler(BookRentedOutException.class)
    protected void bookRentedOutException(BookRentedOutException e, HttpServletResponse response) throws IOException {
        logger.error(e.getMessage());
        response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected void illegalArgumentException(IllegalArgumentException e, HttpServletResponse response) throws IOException {
        logger.error(e.getMessage());
        response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }


}
