package com.teamawsome.domain.service;

import com.teamawsome.domain.book.BookRepository;
import com.teamawsome.domain.member.MemberRepository;
import com.teamawsome.domain.rental.LibrarianRentalDto;
import com.teamawsome.domain.rental.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class Library {
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final RentalRepository rentalRepository;

    @Autowired
    public Library(BookRepository bookRepository, MemberRepository memberRepository, RentalRepository rentalRepository){
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
        this.rentalRepository = rentalRepository;
    }

    public List<LibrarianRentalDto> findRentalsByMember(final String inss){
        return rentalRepository.findOnCondition(rental -> rental.getMember().getInss().equals(inss))
                .stream().map(LibrarianRentalDto::fromRental)
                .collect(Collectors.toUnmodifiableList());
    }

    public void deleteBook(String isbn){
        bookRepository.deleteBook(isbn);
    }

    public void restoreBook(String isbn){
        bookRepository.restoreBook(isbn);
    }

}
