package com.teamawsome.domain.service;

import com.teamawsome.domain.book.Author;
import com.teamawsome.domain.book.Book;
import com.teamawsome.domain.book.BookRepository;
import com.teamawsome.domain.member.Member;
import com.teamawsome.domain.member.MemberRepository;
import com.teamawsome.domain.rental.Rental;
import com.teamawsome.domain.dto.LibrarianRentalDto;
import com.teamawsome.domain.rental.RentalRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;


public class LibraryTest {
    private BookRepository bookRepository = Mockito.mock(BookRepository.class);
    private MemberRepository memberRepository = Mockito.mock(MemberRepository.class);
    private RentalRepository rentalRepository = Mockito.mock(RentalRepository.class);
    private Library library = new Library(bookRepository, memberRepository, rentalRepository, new BookMapper(), new RentalMapper());

    @Test
    public void getRentalsByMember_WithNoRentals_ReturnsEmptyList(){
        Mockito.when(rentalRepository.findOnCondition(Mockito.any())).thenReturn(List.of());
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
        final Book bookOne = new Book(
                new Author("Uresh", "Vahalia"),
                "0131019082",
                "UNIX Internals - The new frontiers",
                "This book examines recent advances in modern UNIX systems."
        );
        Rental rentalOne = new Rental(memberOne, bookOne);
        LibrarianRentalDto expected = new RentalMapper().toLibrarianRentalDto(rentalOne);
        Mockito.when(rentalRepository.findOnCondition(Mockito.any())).thenReturn(List.of(rentalOne));

        List<LibrarianRentalDto> actual = library.findRentalsByMember(memberOne.getInss());

        Assertions.assertThat(actual)
                .isNotNull()
                .hasSize(1)
                .hasSameElementsAs(List.of(expected));

    }


}
