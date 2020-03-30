package com.teamawsome.api.rental;

import com.teamawsome.domain.book.Author;
import com.teamawsome.domain.book.Book;
import com.teamawsome.domain.book.BookNotPresentException;
import com.teamawsome.domain.book.BookRepository;
import com.teamawsome.domain.member.Member;
import com.teamawsome.domain.member.MemberRepository;
import com.teamawsome.domain.rental.BookRentedOutException;
import com.teamawsome.domain.rental.Rental;
import com.teamawsome.domain.rental.RentalNotFoundException;
import com.teamawsome.domain.rental.RentalRepository;
import com.teamawsome.domain.service.Library;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RentalControllerTest {
    private Library getLibrary(){
        return new Library(new BookRepository(), new MemberRepository(), new RentalRepository());
    }

    @Test
    void rentBook_ifGivenBookDoesNotExist_throwException() {
        //given
        RentalController rentalController = new RentalController(new BookRepository(), new RentalRepository(), new MemberRepository(), getLibrary());
        RentBookDto rentBookDto = new RentBookDto("whatever", 1);
        //when
        //then
        assertThatThrownBy(() -> rentalController.rentBook(rentBookDto)).isInstanceOf(BookNotPresentException.class);
    }

    @Test
    void rentBook_ifGivenBookExistsAndIsAvailable_returnsDetailsOfBookAndReturnDate() {
        //given
        BookRepository bookRepository = new BookRepository();
        MemberRepository memberRepository = new MemberRepository();
        RentalController rentalController = new RentalController(bookRepository, new RentalRepository(), memberRepository, getLibrary());
        Author author = new Author("Leo", "Tolstoj");
        Book book = new Book(author, "123456", "War and Peace", "It's a novel");
        bookRepository.addBook(book);
        Member member = Member.MemberBuilder.buildMember()
                .withInss("00000000097")
                .withEmail("tom@gm.com")
                .withFirstName("tom")
                .withLastName("dc")
                .withStreetName("straat")
                .withHouseNumber(5)
                .withPostalCode(9000)
                .withCity("Gent")
                .build();
        memberRepository.addMember(member);
        int memberId = memberRepository.getAllMembers().get(0).getId();

        //when
        RentBookDto bookToBeRentedDto = new RentBookDto("123456", memberId);
        RentalDto actualRentalDto = rentalController.rentBook(bookToBeRentedDto);

        //then
        assertThat(actualRentalDto.getIsbn()).isEqualTo("123456");
    }

    @Test
    void rentBook_ifGivenBookExistsButIsNotAvailable_throwExceptionWithSuitableMessage() {
        //given
        BookRepository bookRepository = new BookRepository();
        MemberRepository memberRepository = new MemberRepository();
        RentalController rentalController = new RentalController(bookRepository, new RentalRepository(), memberRepository, getLibrary());
        Author author = new Author("Leo", "Tolstoj");
        Book book = new Book(author, "123456", "War and Peace", "It's a novel");
        bookRepository.addBook(book);
        Member member = Member.MemberBuilder.buildMember()
                .withInss("00000000097")
                .withEmail("tom@gm.com")
                .withFirstName("tom")
                .withLastName("dc")
                .withStreetName("straat")
                .withHouseNumber(5)
                .withPostalCode(9000)
                .withCity("Gent")
                .build();
        memberRepository.addMember(member);
        int memberId = memberRepository.getAllMembers().get(0).getId();
        RentBookDto bookToBeRentedDto = new RentBookDto("123456", memberId);
        rentalController.rentBook(bookToBeRentedDto);

        //then
        assertThatThrownBy(() -> rentalController.rentBook(bookToBeRentedDto)).isInstanceOf(BookRentedOutException.class);
    }

    @Test
    void rentBook_ifGivenMemberDoesNotExist_throwExceptionWithSuitableMessage() {
        //given
        BookRepository bookRepository = new BookRepository();
        RentalController rentalController = new RentalController(bookRepository, new RentalRepository(), new MemberRepository(), getLibrary());
        Author author = new Author("Leo", "Tolstoj");
        Book book = new Book(author, "123456", "War and Peace", "It's a novel");
        bookRepository.addBook(book);
        RentBookDto bookToBeRentedDto = new RentBookDto("123456", 1);

        //then
        assertThatThrownBy(() -> rentalController.rentBook(bookToBeRentedDto)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void returnedBook_ifCurrentRentalId_returnAllInfo(){
        //given
        MemberRepository memberRepository=new MemberRepository();
        BookRepository bookRepository=new BookRepository();
        RentalRepository rentalRepository=new RentalRepository();
        RentalController rentalController=new RentalController(bookRepository,rentalRepository, memberRepository, getLibrary());
        Author author = new Author("Leo", "Tolstoj");
        Book book = new Book(author, "123456", "War and Peace", "It's a novel");
        bookRepository.addBook(book);
        Member member = Member.MemberBuilder.buildMember()
                .withInss("00000000097")
                .withEmail("tom@gm.com")
                .withFirstName("tom")
                .withLastName("dc")
                .withStreetName("straat")
                .withHouseNumber(5)
                .withPostalCode(9000)
                .withCity("Gent")
                .build();
        Rental newRental=rentalRepository.add(member, book);

        //when
        ReturnedDto actualResult= rentalController.returnBook(newRental.getRentalId());
        ReturnedDto expectedResult=new ReturnedDto(newRental.getRentalId(), member.getId(), book.getISBN(), "You're on time!");
        //then
        Assertions.assertThat(actualResult).isEqualTo(expectedResult);

    }
    @Test
    void returnedBook_ifCurrentRentalIdNotOK_throwException(){
       //given
        MemberRepository memberRepository=new MemberRepository();
        BookRepository bookRepository=new BookRepository();
        RentalRepository rentalRepository=new RentalRepository();
        RentalController rentalController=new RentalController(bookRepository,rentalRepository, memberRepository, getLibrary());
       //when

       //then
        Assertions.assertThatThrownBy(()->rentalController.returnBook(1235)).isInstanceOf(RentalNotFoundException.class);
    }


}