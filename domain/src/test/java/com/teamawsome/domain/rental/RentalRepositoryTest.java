package com.teamawsome.domain.rental;

import com.teamawsome.domain.book.Author;
import com.teamawsome.domain.book.Book;
import com.teamawsome.domain.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class RentalRepositoryTest {
    @Test
    public void returnRentalsBasedOnCondition(){
        RentalRepository repository = new RentalRepository();
        final Member member = new Member("68060105329","harald@somewhere.com","Harald", "Brinkhof",
                "some street", 12, 1234, "Paradise City");

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

        List<Rental> actual = repository.findOnCondition(rental -> rental.getMember().getInss().equals("12345"));

        Assertions.assertThat(actual).hasSameElementsAs(List.of());
    }


}
