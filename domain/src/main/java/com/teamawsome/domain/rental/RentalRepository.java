package com.teamawsome.domain.rental;

import com.teamawsome.domain.book.Book;
import com.teamawsome.domain.member.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RentalRepository {

    List<Rental> rentalList;

    @Autowired
    public RentalRepository() {
        this.rentalList = new ArrayList<>();
    }


    public boolean isAvailable(Book book) {
        for (Rental rental : rentalList) {
            if (rental.getBook().equals(book)) {
                throw new BookRentedOutException();

            }
        }
        return true;
    }


    public Rental add(Member member, Book book) {
        Rental newRental = new Rental(member, book);
        rentalList.add(newRental);
        return newRental;
    }
}
