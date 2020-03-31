package com.teamawsome.api.rental;

import com.teamawsome.domain.book.Author;
import com.teamawsome.domain.book.Book;
import com.teamawsome.domain.book.BookNotPresentException;
import com.teamawsome.domain.book.BookRepository;
import com.teamawsome.domain.dto.RentBookDto;
import com.teamawsome.domain.dto.RentalDto;
import com.teamawsome.domain.dto.ReturnedDto;
import com.teamawsome.domain.member.Member;
import com.teamawsome.domain.member.MemberRepository;
import com.teamawsome.domain.rental.BookRentedOutException;
import com.teamawsome.domain.rental.Rental;
import com.teamawsome.domain.rental.RentalNotFoundException;
import com.teamawsome.domain.rental.RentalRepository;
import com.teamawsome.domain.service.BookMapper;
import com.teamawsome.domain.service.LibraryManagement;
import com.teamawsome.domain.service.RentalMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RentalControllerTest {
    private LibraryManagement getLibrary(){
        return new LibraryManagement(new BookRepository(), new MemberRepository(), new RentalRepository(), new BookMapper(), new RentalMapper());
    }





}