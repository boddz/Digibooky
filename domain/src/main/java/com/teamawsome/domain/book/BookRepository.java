package com.teamawsome.domain.book;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class BookRepository {
    List<Book> bookList=new ArrayList<>();


    public List<Book> getAllBooks(){
        return bookList;
    }


    public Book addBook(Book book) {
        bookList.add(book);
        return book;

    }
    public Book getBook(String ISBN){
        return bookList.stream()
                .filter(book -> book.getISBN().equals(ISBN))
                .findFirst()
                .orElseThrow(BookNotPresentException::new);
    }

    public List<Book> findByISBNWildCard(final String wildcard){
        Predicate<Book> matchOnISBN = book -> book.getISBN().matches(constructRegExFromWildCard(wildcard));

        return bookList.stream()
                .filter(matchOnISBN)
                .collect(Collectors.toUnmodifiableList());
    }
    public List<Book> findByAuthorName(final Author wildcard){
        Predicate<Book> matchOnNames =  book -> book.getAuthor().getFirstName().matches(constructRegExFromWildCard(wildcard.getFirstName())) ||
                                        book.getAuthor().getLastName().matches(constructRegExFromWildCard(wildcard.getLastName()));

        return bookList.stream()
                .filter(matchOnNames)
                .collect(Collectors.toUnmodifiableList());
    }
    public List<Book> findByTitle(final String wildcard){
        Predicate<Book> matchOnTitle = book -> book.getTitle() != null && book.getTitle().matches(constructRegExFromWildCard(wildcard));

        return bookList.stream()
                .filter(matchOnTitle)
                .collect(Collectors.toUnmodifiableList());
    }

    String constructRegExFromWildCard(String input){
        return input.replace("*",".*").replace("?",".?");
    }
}
