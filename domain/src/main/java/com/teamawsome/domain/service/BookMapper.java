package com.teamawsome.domain.service;


import com.teamawsome.domain.book.Author;
import com.teamawsome.domain.book.Book;
import com.teamawsome.domain.dto.BookAddedDto;
import com.teamawsome.domain.dto.BookDto;
import com.teamawsome.domain.dto.DetailedBookDto;
import com.teamawsome.domain.member.Member;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public BookDto toBookDto(Book book){
        return new BookDto(book.getAuthor(), book.getISBN(), book.getTitle(), book.getSummary());
    }
    public Book toBook(BookDto bookDto){
        return new Book(bookDto.getAuthor(), bookDto.getISBN(), bookDto.getTitle(), bookDto.getSummary());
    }
    public Book toBook(BookAddedDto bookAddedDto){
       return new Book(new Author(bookAddedDto.getFirstName(), bookAddedDto.getLastName()),
               bookAddedDto.getIsbn(), bookAddedDto.getTitle(), bookAddedDto.getSummary());
    }

    public DetailedBookDto toDetailedBookDto(Book book , Member member , boolean isRentedOut) {
        return new DetailedBookDto(book.getAuthor(), book.getISBN(), book.getTitle(), book.getSummary(), isRentedOut, member);
    }
}
