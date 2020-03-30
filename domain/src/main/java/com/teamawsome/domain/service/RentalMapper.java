package com.teamawsome.domain.service;

import com.teamawsome.domain.dto.LibrarianRentalDto;
import com.teamawsome.domain.rental.Rental;
import org.springframework.stereotype.Service;

@Service
public class RentalMapper {

    public  LibrarianRentalDto toLibrarianRentalDto(Rental rental){
        return new LibrarianRentalDto(rental.getRentalId(), rental.getBook(), rental.getMember(),
                rental.getStartingDate(), rental.getReturnDate());
    }
}
