package com.teamawsome.domain.book;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
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

        return findBasedOnCondition(matchOnISBN);
    }
    public List<Book> findByAuthorName(final Author wildcard){
        Predicate<Book> matchOnNames =  book -> book.getAuthor().getFirstName().matches(constructRegExFromWildCard(wildcard.getFirstName())) ||
                                        book.getAuthor().getLastName().matches(constructRegExFromWildCard(wildcard.getLastName()));

        return findBasedOnCondition(matchOnNames);
    }
    public List<Book> findByTitle(final String wildcard){
        Predicate<Book> matchOnTitle = book -> book.getTitle() != null && book.getTitle().matches(constructRegExFromWildCard(wildcard));

        return findBasedOnCondition(matchOnTitle);
    }

    private List<Book> findBasedOnCondition(Predicate<Book> condition) {
        return bookList.stream().filter(condition).collect(Collectors.toUnmodifiableList());
    }

    String constructRegExFromWildCard(String input){
        return input.replace("*",".*").replace("?",".?");
    }

    public Book changeBook(String isbn, String firstName, String lastName, String summary, String title) {
        Book bookToModify = getBook(isbn);
        bookToModify.setAuthor(lastName,firstName);
        bookToModify.setTitle(title);
        bookToModify.setSummary(summary);
        return bookToModify;
    }
}
