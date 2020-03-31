package com.teamawsome.domain.service;

import com.teamawsome.domain.book.Author;
import com.teamawsome.domain.book.Book;
import com.teamawsome.domain.book.BookNotPresentException;
import com.teamawsome.domain.book.BookRepository;
import com.teamawsome.domain.dto.*;
import com.teamawsome.domain.member.Member;
import com.teamawsome.domain.member.MemberRepository;
import com.teamawsome.domain.rental.BookRentedOutException;
import com.teamawsome.domain.rental.Rental;
import com.teamawsome.domain.rental.RentalNotFoundException;
import com.teamawsome.domain.rental.RentalRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


public class LibraryManagementTest {
    private BookRepository bookRepository = Mockito.mock(BookRepository.class);
    private MemberRepository memberRepository = Mockito.mock(MemberRepository.class);
    private RentalRepository rentalRepository = Mockito.mock(RentalRepository.class);
    private LibraryManagement libraryManagement = new LibraryManagement(bookRepository, memberRepository, rentalRepository, new BookMapper(), new RentalMapper());

    @Test
    public void getRentalsByMember_WithNoRentals_ReturnsEmptyList() {
        Mockito.when(rentalRepository.findOnCondition(Mockito.any())).thenReturn(List.of());
        List<LibrarianRentalDto> actual = libraryManagement.findRentalsByMember("68060105329");

        Assertions.assertThat(actual).isNotNull().hasSize(0);
    }

    @Test
    public void getRentalsByMember_WithRentals_ReturnsAllRentals() {
        final Member memberOne = Member.MemberBuilder.buildMember()
                .withInss("68060105329")
                .withEmail("harald@somewhere.com")
                .withFirstName("Harald")
                .withLastName("Brinkhof")
                .withStreetName("some street")
                .withHouseNumber(12)
                .withPostalCode(1234)
                .withCity("Paradise City")
                .build();
        final Book bookOne = new Book(
                new Author("Uresh", "Vahalia"),
                "0131019082",
                "UNIX Internals - The new frontiers",
                "This book examines recent advances in modern UNIX systems."
        );
        Rental rentalOne = new Rental(0, memberOne, bookOne);
        LibrarianRentalDto expected = new RentalMapper().toLibrarianRentalDto(rentalOne);
        Mockito.when(rentalRepository.findOnCondition(Mockito.any())).thenReturn(List.of(rentalOne));

        List<LibrarianRentalDto> actual = libraryManagement.findRentalsByMember(memberOne.getInss());

        Assertions.assertThat(actual)
                .isNotNull()
                .hasSize(1)
                .hasSameElementsAs(List.of(expected));

    }

    @Test
    public void getAllOverDueRentals() {
        final Member member = Member.MemberBuilder.buildMember()
                .withInss("68060105329")
                .withEmail("harald@somewhere.com")
                .withFirstName("Harald")
                .withLastName("Brinkhof")
                .withStreetName("some street")
                .withHouseNumber(12)
                .withPostalCode(1234)
                .withCity("Paradise City")
                .build();

        final Book bookOne = new Book(
                new Author("Uresh", "Vahalia"),
                "0131019082",
                "UNIX Internals - The new frontiers",
                "This book examines recent advances in modern UNIX systems."
        );
        Rental input = new Rental(0, member, bookOne, LocalDate.now());
        Mockito.when(rentalRepository.getAllOverDueRentals()).thenReturn(List.of(input));
        RentalDto expected = new RentalMapper().toRentalDto(input);

        Assertions.assertThat(libraryManagement.getAllOverDueRentals()).hasSize(1).hasSameElementsAs(List.of(expected));
    }

    @Test
    void rentBook_ifGivenMemberDoesNotExist_throwExceptionWithSuitableMessage() {
        //given
        Author author = new Author("Leo", "Tolstoj");
        Book book = new Book(author, "123456", "War and Peace", "It's a novel");
        bookRepository.addBook(book);
        RentBookDto bookToBeRentedDto = new RentBookDto("123456", 1);

        LibraryManagement libraryManagement = new LibraryManagement(bookRepository, new MemberRepository(), new RentalRepository(),
                new BookMapper(), new RentalMapper());
        //then
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> libraryManagement.rentBook(bookToBeRentedDto));
    }

    @Test
    void returnedBook_ifCurrentRentalIdNotOK_throwException() {
        Mockito.when(rentalRepository.returnRentedBook(1235)).thenThrow(RentalNotFoundException.class);
        Assertions.assertThatThrownBy(() -> libraryManagement.getReturn(1235)).isInstanceOf(RentalNotFoundException.class);
    }

    @Test
    void rentBook_ifGivenBookExistsButIsNotAvailable_throwExceptionWithSuitableMessage() {
        //given
        BookRepository bookRepository = new BookRepository();
        MemberRepository memberRepository = new MemberRepository();
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

        LibraryManagement libraryManagement = new LibraryManagement(bookRepository, memberRepository, new RentalRepository(), new BookMapper(), new RentalMapper());
        libraryManagement.rentBook(bookToBeRentedDto);
        //then
        assertThatThrownBy(() -> libraryManagement.rentBook(bookToBeRentedDto)).isInstanceOf(BookRentedOutException.class);
    }


    @Test
    void returnedBook_ifCurrentRentalId_returnAllInfo() {
        //given
        MemberRepository memberRepository = new MemberRepository();
        BookRepository bookRepository = new BookRepository();
        RentalRepository rentalRepository = new RentalRepository();

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
        Rental newRental = rentalRepository.add(member, book);

        LibraryManagement libraryManagement = new LibraryManagement(bookRepository, memberRepository, rentalRepository, new BookMapper(), new RentalMapper());
        //when
        ReturnedDto actualResult = libraryManagement.getReturn(newRental.getRentalId());
        ReturnedDto expectedResult = new ReturnedDto(newRental.getRentalId(), member.getId(), book.getISBN(), "You're on time!");
        //then
        Assertions.assertThat(actualResult).isEqualTo(expectedResult);

    }

    @Test
    void rentBook_ifGivenBookExistsAndIsAvailable_returnsDetailsOfBookAndReturnDate() {
        //given
        BookRepository bookRepository = new BookRepository();
        MemberRepository memberRepository = new MemberRepository();
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
        LibraryManagement libraryManagement = new LibraryManagement(bookRepository, memberRepository, new RentalRepository(), new BookMapper(), new RentalMapper());

        //when
        RentBookDto bookToBeRentedDto = new RentBookDto("123456", memberId);
        RentalDto actualRentalDto = libraryManagement.rentBook(bookToBeRentedDto);

        //then
        assertThat(actualRentalDto.getIsbn()).isEqualTo("123456");
    }

    @Test
    void getDetailsOfBook_withBooklentOut() {
        BookRepository bookRepository = new BookRepository();
        MemberRepository memberRepository = new MemberRepository();
        RentalRepository rentalRepository = new RentalRepository();
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
        Author author = new Author("Leo", "Tolstoj");
        Book book = new Book(author, "123456", "War and Peace", "It's a novel");
        bookRepository.addBook(book);
        rentalRepository.add(member, book);

        LibraryManagement libraryManagement = new LibraryManagement(bookRepository, memberRepository, rentalRepository, new BookMapper(), new RentalMapper());


        DetailedBookDto actual = libraryManagement.getDetailsOfBook(book.getISBN());
        DetailedBookDto expected = new BookMapper().toDetailedBookDto(book, member, true);

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void getDetailsOfBook_withBookNotlentOut() {
        BookRepository bookRepository = new BookRepository();
        MemberRepository memberRepository = new MemberRepository();
        RentalRepository rentalRepository = new RentalRepository();
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
        Author author = new Author("Leo", "Tolstoj");
        Book book = new Book(author, "123456", "War and Peace", "It's a novel");
        bookRepository.addBook(book);


        LibraryManagement libraryManagement = new LibraryManagement(bookRepository, memberRepository, rentalRepository, new BookMapper(), new RentalMapper());


        DetailedBookDto actual = libraryManagement.getDetailsOfBook(book.getISBN());
        DetailedBookDto expected = new BookMapper().toDetailedBookDto(book, null, false);

        Assertions.assertThat(actual).isEqualTo(expected);
    }
}
