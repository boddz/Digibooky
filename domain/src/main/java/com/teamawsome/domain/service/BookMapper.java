package com.teamawsome.domain.service;


import com.teamawsome.domain.book.Author;
import com.teamawsome.domain.book.Book;
import com.teamawsome.domain.dto.BookAddedDto;
import com.teamawsome.domain.dto.BookDto;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public BookDto transformBookToBookDto(Book book){
        return new BookDto(book.getAuthor(), book.getISBN(), book.getTitle(), book.getSummary());
    }
    public Book transformBookDtoToBook(BookDto bookDto){
        return new Book(bookDto.getAuthor(), bookDto.getISBN(), bookDto.getTitle(), bookDto.getSummary());
    }
    public Book transformBookAddedDtoBook(BookAddedDto bookAddedDto){
       return new Book(new Author(bookAddedDto.getFirstName(), bookAddedDto.getLastName()),
               bookAddedDto.getIsbn(), bookAddedDto.getTitle(), bookAddedDto.getSummary());
    }
}
