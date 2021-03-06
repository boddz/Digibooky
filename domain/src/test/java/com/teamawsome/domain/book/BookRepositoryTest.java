package com.teamawsome.domain.book;

import com.teamawsome.domain.dto.FindByAuthorDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

class BookRepositoryTest {
        private void fillWithUnimportantBook(BookRepository repository) {
            Book one = new Book(
                    new Author("Uresh", "Vahalia"),
                    "0131019082",
                    "UNIX Internals - The new frontiers",
                    "This book examines recent advances in modern UNIX systems."
            );

            repository.addBook(one);
        }

        @Test
        void getAllBooks_returnsListOfAllBooks() {
            //given
            BookRepository bookRepository = new BookRepository();
            Author dries= new Author("Dries", "Bodaer");
            Book book1 = new Book(dries, "1234567890", "An excellent book", "Very interesting book");
            bookRepository.addBook(book1);

            Book book2= new Book(dries, "1234567890", "An aweful book", "Very aweful book");
            bookRepository.addBook(book2);

            //when
            List<Book> listOfBook=bookRepository.getAllBooks();

            //then
            Assertions.assertThat(listOfBook).containsExactlyInAnyOrder(book1, book2);
    }

    @Test
    public void transformWildCardSearchStringToRegExp(){
            BookRepository repository = new BookRepository();
            String expected = "A.?.*";
            String input = "A?*";

            Assertions.assertThat(repository.constructRegExFromWildCard(input)).isEqualTo(expected);
    }

    @Test
    public void findBooksByISBNWildCard(){
        BookRepository repository = new BookRepository();
        Book one = new Book(
                new Author("Uresh", "Vahalia"),
                "0131019082",
                "UNIX Internals - The new frontiers",
                "This book examines recent advances in modern UNIX systems."
        );
        Book two = new Book(
                new Author("Igor","Zhirkov"),
                "9781484224021",
                "Low-level Programming",
                "Low-level Programming explains Intel 64 architecture as the result of Von Neumann architecture evolution"
        );
        Book three = new Book(
                new Author("Joshua", "Bloch"),
                "9780134685991",
                "Effective Java",
                "The definitive guide to Java Platform best practices"
        );

        repository.addBook(one);
        repository.addBook(two);
        repository.addBook(three);
        List<Book> expected = List.of(two, three);
        List<Book> actual = repository.findByISBNWildCard("978*");

        Assertions.assertThat(actual).hasSameElementsAs(expected);
    }

    @Test
    public void findByISBNWildCard_WithEmptyWildCardString_ReturnsEmptyList(){
        BookRepository repository = new BookRepository();
        fillWithUnimportantBook(repository);

        Assertions.assertThat(repository.findByISBNWildCard("")).hasSize(0);
    }


    @Test
    public void findByAuthorName_withAllFieldsEmpty_ReturnsEmptyList(){
        BookRepository repository = new BookRepository();

        FindByAuthorDto author = new FindByAuthorDto("","");
        fillWithUnimportantBook(repository);

        Assertions.assertThat(repository.findByAuthorName(author)).hasSize(0);
    }

    @Test
    public void findByAuthorName_withFirstNameSet(){
        BookRepository repository = new BookRepository();

        FindByAuthorDto author = new FindByAuthorDto("U*h*","");
        Book one = new Book(
                new Author("Uresh", "Vahalia"),
                "0131019082",
                "UNIX Internals - The new frontiers",
                "This book examines recent advances in modern UNIX systems."
        );
        Book two = new Book(
                new Author("Igor","Zhirkov"),
                "9781484224021",
                "Low-level Programming",
                "Low-level Programming explains Intel 64 architecture as the result of Von Neumann architecture evolution"
        );
        Book three = new Book(
                new Author("Joshua", "Bloch"),
                "9780134685991",
                "Effective Java",
                "The definitive guide to Java Platform best practices"
        );

        repository.addBook(one);
        repository.addBook(two);
        repository.addBook(three);

        Assertions.assertThat(repository.findByAuthorName(author)).hasSize(1);
        Assertions.assertThat(repository.findByAuthorName(author)).hasSameElementsAs(List.of(one));
    }

    @Test
    public void findByAuthorName_withLastNameSet(){
        BookRepository repository = new BookRepository();

        FindByAuthorDto author = new FindByAuthorDto("","*h*");
        Book one = new Book(
                new Author("Uresh", "Vahalia"),
                "0131019082",
                "UNIX Internals - The new frontiers",
                "This book examines recent advances in modern UNIX systems."
        );
        Book two = new Book(
                new Author("Igor","Zhirkov"),
                "9781484224021",
                "Low-level Programming",
                "Low-level Programming explains Intel 64 architecture as the result of Von Neumann architecture evolution"
        );
        Book three = new Book(
                new Author("Joshua", "Bloch"),
                "9780134685991",
                "Effective Java",
                "The definitive guide to Java Platform best practices"
        );

        repository.addBook(one);
        repository.addBook(two);
        repository.addBook(three);

        Assertions.assertThat(repository.findByAuthorName(author)).hasSize(3);
        Assertions.assertThat(repository.findByAuthorName(author)).hasSameElementsAs(List.of(one, two, three));
    }

    @Test
    public void findByTitle(){
        BookRepository repository = new BookRepository();

        Book one = new Book(
                new Author("Uresh", "Vahalia"),
                "0131019082",
                "UNIX Internals - The new frontiers",
                "This book examines recent advances in modern UNIX systems."
        );
        Book two = new Book(
                new Author("Igor","Zhirkov"),
                "9781484224021",
                "Low-level Programming",
                "Low-level Programming explains Intel 64 architecture as the result of Von Neumann architecture evolution"
        );
        Book three = new Book(
                new Author("Joshua", "Bloch"),
                "9780134685991",
                "Effective Java",
                "The definitive guide to Java Platform best practices"
        );

        repository.addBook(one);
        repository.addBook(two);
        repository.addBook(three);

        Assertions.assertThat(repository.findByTitle("")).hasSize(0);
        Assertions.assertThat(repository.findByTitle("*new*")).hasSize(1);
        Assertions.assertThat(repository.findByTitle("*new*")).hasSameElementsAs(List.of(one));
    }

    @Test
    public void booksCanBeDeleted_afterBeingDeletedTheyNoLongerShowUpInSearches(){
        BookRepository repository = new BookRepository();

        Book one = new Book(
                new Author("Uresh", "Vahalia"),
                "0131019082",
                "UNIX Internals - The new frontiers",
                "This book examines recent advances in modern UNIX systems."
        );
        Book two = new Book(
                new Author("Igor","Zhirkov"),
                "9781484224021",
                "Low-level Programming",
                "Low-level Programming explains Intel 64 architecture as the result of Von Neumann architecture evolution"
        );
        Book three = new Book(
                new Author("Joshua", "Bloch"),
                "9780134685991",
                "Effective Java",
                "The definitive guide to Java Platform best practices"
        );

        repository.addBook(one);
        repository.addBook(two);
        repository.addBook(three);

        Assertions.assertThat(repository.getAllBooks()).hasSize(3);

        Optional<Book> actual = repository.deleteBook(three.getISBN());
        Assertions.assertThat(repository.getAllBooks()).hasSize(2).hasSameElementsAs(List.of(one, two));
        Assertions.assertThat(actual).isNotEmpty().contains(three);
        Assertions.assertThat(actual.get().isDestroyed()).isTrue();
    }

    @Test
    public void deletedBooksCanBeRestored_afterRestoringTheyShowUpInSearchesAgain(){

        BookRepository repository = new BookRepository();

        Book one = new Book(
                new Author("Uresh", "Vahalia"),
                "0131019082",
                "UNIX Internals - The new frontiers",
                "This book examines recent advances in modern UNIX systems."
        );
        Book two = new Book(
                new Author("Igor","Zhirkov"),
                "9781484224021",
                "Low-level Programming",
                "Low-level Programming explains Intel 64 architecture as the result of Von Neumann architecture evolution"
        );
        Book three = new Book(
                new Author("Joshua", "Bloch"),
                "9780134685991",
                "Effective Java",
                "The definitive guide to Java Platform best practices"
        );

        repository.addBook(one);
        repository.addBook(two);
        three.setDestroyed(true);
        repository.addBook(three);

        Assertions.assertThat(repository.getAllBooks()).hasSize(2);

        Optional<Book> actual = repository.restoreBook(three.getISBN());
        Assertions.assertThat(repository.getAllBooks()).hasSize(3).hasSameElementsAs(List.of(one, two, three));
        Assertions.assertThat(actual).isNotEmpty().contains(three);
        Assertions.assertThat(actual.get().isDestroyed()).isFalse();
    }


}