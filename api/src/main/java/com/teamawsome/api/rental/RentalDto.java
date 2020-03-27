package com.teamawsome.api.rental;

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
}
