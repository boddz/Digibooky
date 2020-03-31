package com.teamawsome.domain.dto;

import java.time.LocalDate;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RentalDto rentalDto = (RentalDto) o;
        return rentalId == rentalDto.rentalId &&
                memberId == rentalDto.memberId &&
                Objects.equals(isbn, rentalDto.isbn) &&
                Objects.equals(returnDate, rentalDto.returnDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rentalId, memberId, isbn, returnDate);
    }
}
