package com.teamawsome.domain.service;

import com.teamawsome.domain.book.BookRepository;
import com.teamawsome.domain.dto.BookAddedDto;
import com.teamawsome.domain.dto.BookDto;
import com.teamawsome.domain.member.MemberRepository;
import com.teamawsome.domain.dto.LibrarianRentalDto;
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
    private final BookMapper bookMapper;
    private final RentalMapper rentalMapper;

    @Autowired
    public Library(BookRepository bookRepository, MemberRepository memberRepository, RentalRepository rentalRepository,
                   BookMapper bookMapper, RentalMapper rentalMapper){
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
        this.rentalRepository = rentalRepository;
        this.bookMapper = bookMapper;
        this.rentalMapper = rentalMapper;
    }

    public List<LibrarianRentalDto> findRentalsByMember(final String inss){
        return rentalRepository.findOnCondition(rental -> rental.getMember().getInss().equals(inss))
                .stream().map(rentalMapper::toLibrarianRentalDto)
                .collect(Collectors.toUnmodifiableList());
    }

    public void deleteBook(String isbn){
        bookRepository.deleteBook(isbn);
    }

    public void restoreBook(String isbn){
        bookRepository.restoreBook(isbn);
    }

    public BookDto changeBook(BookAddedDto bookToChange){
        return bookMapper.toBookDto(bookRepository.changeBook(bookMapper.toBook(bookToChange)));
    }
}
