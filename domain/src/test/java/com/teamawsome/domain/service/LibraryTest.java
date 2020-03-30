package com.teamawsome.domain.service;

import com.teamawsome.domain.book.Author;
import com.teamawsome.domain.book.Book;
import com.teamawsome.domain.book.BookRepository;
import com.teamawsome.domain.member.Member;
import com.teamawsome.domain.member.MemberRepository;
import com.teamawsome.domain.rental.Rental;
import com.teamawsome.domain.rental.LibrarianRentalDto;
import com.teamawsome.domain.rental.RentalRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
public class LibraryTest {

    @Test
    public void getRentalsByMember_WithNoRentals_ReturnsEmptyList(){
        Library library = new Library(new BookRepository(), new MemberRepository(), new RentalRepository());
        List<LibrarianRentalDto> actual = library.findRentalsByMember("68060105329");

        Assertions.assertThat(actual).isNotNull().hasSize(0);
    }
    @Test public void getRentalsByMember_WithRentals_ReturnsAllRentals(){
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
        final Member memberTwo = Member.MemberBuilder.buildMember()
                .withInss("68072101358")
                .withEmail("tim@somewhere.com")
                .withFirstName("Tim")
                .withLastName("Niemand")
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
        final Book bookTwo = new Book(
                new Author("Igor","Zhirkov"),
                "9781484224021",
                "Low-level Programming",
                "Low-level Programming explains Intel 64 architecture as the result of Von Neumann architecture evolution"
        );
        RentalRepository rentalRepository = new RentalRepository();

        Rental rentalOne = rentalRepository.add(memberOne, bookOne);
        rentalRepository.add(memberTwo, bookTwo);
        LibrarianRentalDto expected = LibrarianRentalDto.fromRental(rentalOne);
        Library library = new Library(new BookRepository(), new MemberRepository(), rentalRepository);

        List<LibrarianRentalDto> actual = library.findRentalsByMember(memberOne.getInss());

        Assertions.assertThat(actual)
                .isNotNull()
                .hasSize(1)
                .hasSameElementsAs(List.of(expected));

    }


}
