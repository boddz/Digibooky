package com.teamawsome.domain.rental;

import com.teamawsome.domain.book.Author;
import com.teamawsome.domain.book.Book;
import com.teamawsome.domain.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.*;
import java.util.List;

public class RentalRepositoryTest {
    @Test
    public void returnRentalsBasedOnCondition(){
        RentalRepository repository = new RentalRepository();
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
        final Book bookTwo = new Book(
                new Author("Igor","Zhirkov"),
                "9781484224021",
                "Low-level Programming",
                "Low-level Programming explains Intel 64 architecture as the result of Von Neumann architecture evolution"
        );
        Rental one = repository.add(member, bookOne);
        Rental two = repository.add(member, bookTwo);

        List<Rental> actualAll = repository.findOnCondition(rental -> rental.getMember().getInss().equals(member.getInss()));
        List<Rental> actualBookTwo = repository.findOnCondition(rental -> rental.getBook().getISBN().equals(bookTwo.getISBN()));
        List<Rental> actualEmpty = repository.findOnCondition(rental -> rental.getBook().getISBN() == null);

        Assertions.assertThat(actualAll).hasSameElementsAs(List.of(one, two));
        Assertions.assertThat(actualBookTwo).hasSameElementsAs(List.of(two));
        Assertions.assertThat(actualEmpty).hasSameElementsAs(List.of());
    }

    @Test
    public void returnRentalsBasedOnCondition_WithEmptyRepository_ReturnsEmptyList(){
        RentalRepository repository = new RentalRepository();

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
        final Book bookTwo = new Book(
                new Author("Igor","Zhirkov"),
                "9781484224021",
                "Low-level Programming",
                "Low-level Programming explains Intel 64 architecture as the result of Von Neumann architecture evolution"
        );
        List<Rental> actual = repository.findOnCondition(rental -> rental.getMember().getInss().equals("12345"));

        Assertions.assertThat(actual).hasSameElementsAs(List.of());
    }

    @Test
    public void getAllOverDueRentals(){
        RentalRepository repository = new RentalRepository();
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
        final Book bookTwo = new Book(
                new Author("Igor","Zhirkov"),
                "9781484224021",
                "Low-level Programming",
                "Low-level Programming explains Intel 64 architecture as the result of Von Neumann architecture evolution"
        );

        ZoneId zoneId = ZoneId.systemDefault();
        Clock clock = Clock.fixed(LocalDateTime.now().minusDays(Rental.DEFAULT_RENTING_TIME + 1).atZone(zoneId).toInstant(), zoneId);

        Rental expected = new Rental(0, member, bookOne, LocalDate.now(clock));
        repository.add(expected);
        repository.add(member, bookTwo);
        List<Rental> actual = repository.getAllOverDueRentals();

        Assertions.assertThat(actual).hasSameElementsAs(List.of(expected));
    }

}
