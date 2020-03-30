package com.teamawsome.api.book;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamawsome.domain.book.Author;
import com.teamawsome.domain.book.Book;
import com.teamawsome.domain.book.BookRepository;
import com.teamawsome.domain.dto.BookAddedDto;
import com.teamawsome.domain.dto.BookDto;
import com.teamawsome.domain.member.MemberRepository;
import com.teamawsome.domain.rental.RentalRepository;
import com.teamawsome.domain.service.BookMapper;
import com.teamawsome.domain.dto.FindByAuthorDto;
import com.teamawsome.domain.service.LibraryManagement;
import com.teamawsome.domain.service.RentalMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;


import static org.mockito.ArgumentMatchers.any;

import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

@ComponentScan (basePackages = "com.teamawsome")
@WebMvcTest(BookController.class)
class BookControllerTest {
    @MockBean
    BookRepository bookRepository;
    @Autowired
    MockMvc mockMvc;

    private String objectToJSON(Object object) {
        try{
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e){
            throw new AssertionError("Mapping object to JSON failed", e);
        }
    }

    @Test
    void getAllBooks() throws Exception {
        Author dries = new Author("dries", "bodaer");
        List<Book> returnValue = List.of(new Book(dries, "1354", "booktitle", "summary"),
                                new Book(dries, "1234567890", "An aweful book", "Very aweful book"));

        String expected = objectToJSON(
                                List.of(new BookDto(dries, "1354", "booktitle", "summary"),
                                new BookDto(dries, "1234567890", "An aweful book", "Very aweful book"))
                            );

        Mockito.when(bookRepository.getAllBooks()).thenReturn(returnValue);

        String actual = mockMvc.perform(get("/books")
                    .with(user("user")
                        .password("password")
                        .roles("Admin")))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JSONAssert.assertEquals(expected, actual, JSONCompareMode.STRICT);


    }

    @Test
    public void findBookByISBN() throws Exception{
        List<Book> returnedBooks = List.of(
                new Book(
                    new Author("Igor","Zhirkov"),
                    "9781484224021",
                    "Low-level Programming",
                    "Low-level Programming explains Intel 64 architecture as the result of Von Neumann architecture evolution" ),
                 new Book(
                    new Author("Joshua", "Bloch"),
                    "9780134685991",
                    "Effective Java",
                    "The definitive guide to Java Platform best practices" )
        );
        String expected = objectToJSON(List.of(
                new BookDto(
                        new Author("Igor","Zhirkov"),
                        "9781484224021",
                        "Low-level Programming",
                        "Low-level Programming explains Intel 64 architecture as the result of Von Neumann architecture evolution" ),
                new BookDto(
                        new Author("Joshua", "Bloch"),
                        "9780134685991",
                        "Effective Java",
                        "The definitive guide to Java Platform best practices" )
        ));

        Mockito.when(bookRepository.findByISBNWildCard("013*82")).thenReturn(returnedBooks);
        String actual = mockMvc.perform(get("/books")
                                        .queryParam("withIsbn","013*82")
                                        .with(user("user")
                                                .password("password")
                                                .roles("Admin")) )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JSONAssert.assertEquals(expected, actual, JSONCompareMode.STRICT);
    }


    @Test
    public void findBookByAuthorName() throws Exception{

        Book lowLevel = new Book(
                        new Author("Igor","Zhirkov"),
                        "9781484224021",
                        "Low-level Programming",
                        "Low-level Programming explains Intel 64 architecture as the result of Von Neumann architecture evolution" );
        Book effective = new Book(
                        new Author("Joshua", "Bloch"),
                        "9780134685991",
                        "Effective Java",
                        "The definitive guide to Java Platform best practices" );
        List<Book> returnedBooks =List.of(lowLevel, effective);

        BookDto dtoLowLevel = new BookDto(
                        new Author("Igor","Zhirkov"),
                        "9781484224021",
                        "Low-level Programming",
                        "Low-level Programming explains Intel 64 architecture as the result of Von Neumann architecture evolution" );
        BookDto dtoEffective = new BookDto(
                        new Author("Joshua", "Bloch"),
                        "9780134685991",
                        "Effective Java",
                        "The definitive guide to Java Platform best practices" );
        String expectedAll = objectToJSON(List.of(dtoLowLevel, dtoEffective));
        String expectedLowlevel = objectToJSON(List.of(dtoLowLevel));
        String expectedEffective = objectToJSON(List.of(dtoEffective));
        String expectedEmptyList = objectToJSON(List.<BookDto>of());

        Mockito.when(bookRepository.findByAuthorName(new FindByAuthorDto("","*o*"))).thenReturn(returnedBooks);

        String actual = mockMvc.perform(get("/books")
                .queryParam("withAuthor","{\"firstName\":\"\", \"lastName\":\"*o*\"}")
                .with(user("user")
                        .password("password")
                        .roles("Admin")) )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JSONAssert.assertEquals(expectedAll, actual, JSONCompareMode.STRICT);

        Mockito.when(bookRepository.findByAuthorName(new FindByAuthorDto("?gor",""))).thenReturn(List.of(lowLevel));
        actual = mockMvc.perform(get("/books")
                .queryParam("withAuthor","{\"firstName\":\"?gor\", \"lastName\":\"\"}")
                .with(user("user")
                        .password("password")
                        .roles("Admin")) )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JSONAssert.assertEquals(expectedLowlevel, actual, JSONCompareMode.STRICT);

        Mockito.when(bookRepository.findByAuthorName(new FindByAuthorDto("Jos*","*o*"))).thenReturn(List.of(effective));
        actual = mockMvc.perform(get("/books")
                .queryParam("withAuthor","{\"firstName\":\"Jos*\", \"lastName\":\"*o*\"}")
                .with(user("user")
                        .password("password")
                        .roles("Admin")) )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JSONAssert.assertEquals(expectedEffective, actual, JSONCompareMode.STRICT);

        Mockito.when(bookRepository.findByAuthorName(new FindByAuthorDto("",""))).thenReturn(List.<Book>of());
        actual = mockMvc.perform(get("/books")
                .queryParam("withAuthor","{\"firstName\":null, \"lastName\":null}")
                .with(user("user")
                        .password("password")
                        .roles("Admin")) )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JSONAssert.assertEquals(expectedEmptyList, actual, JSONCompareMode.STRICT);
    }

    @Test
    public void findBookByTitle() throws Exception{

        Book lowLevel = new Book(
                new Author("Igor","Zhirkov"),
                "9781484224021",
                "Low-level Programming",
                "Low-level Programming explains Intel 64 architecture as the result of Von Neumann architecture evolution" );
        Book effective = new Book(
                new Author("Joshua", "Bloch"),
                "9780134685991",
                "Effective Java",
                "The definitive guide to Java Platform best practices" );
        List<Book> returnedBooks =List.of(lowLevel, effective);

        BookDto dtoLowLevel = new BookDto(
                new Author("Igor","Zhirkov"),
                "9781484224021",
                "Low-level Programming",
                "Low-level Programming explains Intel 64 architecture as the result of Von Neumann architecture evolution" );
        BookDto dtoEffective = new BookDto(
                new Author("Joshua", "Bloch"),
                "9780134685991",
                "Effective Java",
                "The definitive guide to Java Platform best practices" );
        String expectedAll = objectToJSON(List.of(dtoLowLevel, dtoEffective));
        String expectedLowlevel = objectToJSON(List.of(dtoLowLevel));
        String expectedEmptyList = objectToJSON(List.<BookDto>of());

        Mockito.when(bookRepository.findByTitle("*a*")).thenReturn(returnedBooks);

        String actual = mockMvc.perform(get("/books")
                .queryParam("withTitle","*a*")
                .with(user("user")
                        .password("password")
                        .roles("Admin")) )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JSONAssert.assertEquals(expectedAll, actual, JSONCompareMode.STRICT);

        Mockito.when(bookRepository.findByTitle("*gramming")).thenReturn(List.of(lowLevel));
        actual = mockMvc.perform(get("/books")
                .queryParam("withTitle","*gramming")
                .with(user("user")
                        .password("password")
                        .roles("Admin")) )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JSONAssert.assertEquals(expectedLowlevel, actual, JSONCompareMode.STRICT);

        Mockito.when(bookRepository.findByTitle("")).thenReturn(List.<Book>of());
        actual = mockMvc.perform(get("/books")
                .queryParam("withTitle","")
                .with(user("user")
                        .password("password")
                        .roles("Admin")) )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JSONAssert.assertEquals(expectedEmptyList, actual, JSONCompareMode.STRICT);
    }

   @Test
    public void addBookToRepository(){
        //GIVEN
        BookRepository bookRepository=new BookRepository();
        Author author=new Author("Tom", "Decrock");
        Book book=new Book(author, "123456", "How to chill with kids", "Tips and tricks to relax while your kids are running around");
        //WHEN
        Book actualResult= bookRepository.addBook(book);
        Author expectedAuthor=new Author ("Tom", "Decrock");
        Book expectedResult=new Book(expectedAuthor, "123456", "How to chill with kids","Tips and tricks to relax while your kids are running around");
        //THEN
       Assertions.assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    public void modifyBook() {
        //GIVEN
        BookRepository bookRepository = new BookRepository();
        LibraryManagement libraryManagement = new LibraryManagement(bookRepository, new MemberRepository(), new RentalRepository(), new BookMapper(), new RentalMapper());
        BookController control = new BookController(bookRepository,new BookMapper(), libraryManagement);
        Author author = new Author("Tom", "Decrock");
        Book old = new Book(author, "123456", "How to chill with kids", "Tips and tricks to relax while your kids are running around");
        bookRepository.addBook(old);
        BookAddedDto changed = new BookAddedDto("123456", "survival", "Boddy", "Dries", "Fucking Test");
        Author controlAuthor = new Author("Dries", "Boddy");
        Book controlll = new Book(controlAuthor, "123456", "survival", "Fucking Test");

        //WHEN
        control.modifyBook(changed);

        //THEN
        Assertions.assertThat(old).isEqualTo(controlll);


    }

}
