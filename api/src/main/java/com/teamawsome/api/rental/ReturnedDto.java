package com.teamawsome.api.rental;

import java.util.Objects;

public class ReturnedDto {
    private int rentalId;
    private int memberId;
    private String isbn;
    private String ontimeMessage;

    public ReturnedDto(int rentalId, int memberId, String isbn, String ontimeMessage) {
        this.rentalId = rentalId;
        this.memberId = memberId;
        this.isbn = isbn;
        this.ontimeMessage = ontimeMessage;
    }

    public int getRentalId() {
        return rentalId;
    }

    public int getMemberId() {
        return memberId;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getOntimeMessage() {
        return ontimeMessage;
    }

    @Override
    public String toString() {
        return "ReturnedDto{" +
                "rentalId=" + rentalId +
                ", memberId=" + memberId +
                ", isbn='" + isbn + '\'' +
                ", ontimeMessage='" + ontimeMessage + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReturnedDto)) return false;
        ReturnedDto that = (ReturnedDto) o;
        return getRentalId() == that.getRentalId() &&
                getMemberId() == that.getMemberId() &&
                Objects.equals(getIsbn(), that.getIsbn()) &&
                Objects.equals(getOntimeMessage(), that.getOntimeMessage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRentalId(), getMemberId(), getIsbn(), getOntimeMessage());
    }
}

