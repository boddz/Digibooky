package com.teamawsome.domain.rental;

import com.teamawsome.domain.book.Book;
import com.teamawsome.domain.member.Member;

import java.util.ArrayList;
import java.util.List;

public class RentalRepository {

    List<Rental> rentalList;

    public RentalRepository() {
        this.rentalList = new ArrayList<>();
    }


    public boolean isAvailable(Book book) {
        for (Rental rental : rentalList){
                if (rental.getBook().equals(book)){
                    throw new BookRentedOutException();

            }
        }
        return true;
    }


    public void add(String MemberId, Book book) {
        rentalList.add(new Rental(book));
    }
}
