package com.teamawsome.domain.dto;

import java.time.LocalDate;

public class RentalDto {

    private int rentalId;
    private int memberId;
    private String isbn;
    private LocalDate returnDate;

    public RentalDto(int lendingNumber, int memberId, String isbn, LocalDate returnDate) {
        this.rentalId = lendingNumber;
        this.memberId = memberId;
        this.isbn = isbn;
        this.returnDate = returnDate;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getRentalId() {
        return rentalId;
    }

    public int getMemberId() {
        return memberId;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }
}
