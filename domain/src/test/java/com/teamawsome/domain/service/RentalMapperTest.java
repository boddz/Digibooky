package com.teamawsome.domain.service;


import com.teamawsome.domain.book.Author;
import com.teamawsome.domain.book.Book;
import com.teamawsome.domain.dto.LibrarianRentalDto;
import com.teamawsome.domain.member.Member;
import com.teamawsome.domain.rental.Rental;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class RentalMapperTest {
    private final RentalMapper rentalMapper = new RentalMapper();

    @Test
    public void rentalToLibrarianRentalDto(){

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
        final Book book = new Book(
                new Author("Uresh", "Vahalia"),
                "0131019082",
                "UNIX Internals - The new frontiers",
                "This book examines recent advances in modern UNIX systems."
        );
        Rental input = new Rental(member, book);
        LibrarianRentalDto expected = new LibrarianRentalDto(1,book, member, input.getStartingDate(), input.getReturnDate());
        Assertions.assertThat(rentalMapper.toLibrarianRentalDto(input)).isEqualTo(expected);
    }
}
