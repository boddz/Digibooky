package com.teamawsome.api.book;


import com.teamawsome.domain.book.Author;
import com.teamawsome.domain.book.Book;
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
       return new Book(new Author(bookAddedDto.firstName, bookAddedDto.lastName), bookAddedDto.isbn, bookAddedDto.title, bookAddedDto.summary);
    }
}
