package com.teamawsome.domain.book;

import com.teamawsome.domain.dto.FindByAuthorDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class BookRepository {
    List<Book> bookList=new ArrayList<>();


    public List<Book> getAllBooks(){
        return bookList.stream().filter(book -> !book.isDestroyed()).collect(Collectors.toUnmodifiableList());
    }


    public Book addBook(Book book) {
        bookList.add(book);
        return book;
    }

    private Optional<Book> getBookEvenIfDeleted(String ISBN){
        return bookList.stream()
                .filter(book -> book.getISBN().equals(ISBN))
                .findFirst();
    }
    public Book getBook(String ISBN){
        Optional<Book> book = getBookEvenIfDeleted(ISBN);
        if(book.isPresent() && !book.get().isDestroyed()){
            return book.get();
        } else {
            throw new BookNotPresentException();
        }
    }

    public List<Book> findByISBNWildCard(final String wildcard){
        Predicate<Book> matchOnISBN = book -> book.getISBN().matches(constructRegExFromWildCard(wildcard));

        return findBasedOnCondition(matchOnISBN);
    }
    public List<Book> findByAuthorName(final FindByAuthorDto wildcard){
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

    public Book changeBook(Book updatedBook) {

        Optional<Book> bookToModify = getBookEvenIfDeleted(updatedBook.getISBN());
        Book toModify = null;
        if(bookToModify.isPresent()) {
            toModify = bookToModify.get();
            toModify.setAuthor(updatedBook.getAuthor());
            toModify.setTitle(updatedBook.getTitle());
            toModify.setSummary(updatedBook.getSummary());
        }
        return toModify;
    }

    public Optional<Book> deleteBook(String isbn){
        Optional<Book> deleted = getBookEvenIfDeleted(isbn);
        deleted.ifPresent(book -> book.setDestroyed(true));
        return deleted;
    }
    public Optional<Book> restoreBook(String isbn){
        Optional<Book> restored = getBookEvenIfDeleted(isbn);
        restored.ifPresent(book -> book.setDestroyed(false));
        return restored;
    }
}
