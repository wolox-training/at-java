package wolox.training.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;
import wolox.training.connectors.BookConnector;
import wolox.training.converters.BookInfoConverter;
import wolox.training.dto.BookDTO;
import wolox.training.dtoConverters.BookDtoConverter;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.exceptions.ErrorConstats;
import wolox.training.exceptions.RequiredArgumentException;
import wolox.training.mocks.MockBook;
import wolox.training.mocks.MockOpenLibraryResponse;
import wolox.training.mocks.MockUser;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UserRepository;
import wolox.training.services.connectors.OpenLibraryRequester;
import wolox.training.services.connectors.dtos.OpenLibraryResponse;
import wolox.training.services.dtos.BookInfoDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase
public class BookIntegrationTests {

    private User mockUser;
    private String password = "123";
    @Autowired
    private BookRepository repository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookConnector bookConnector;

    @LocalServerPort
    private int port;

    @MockBean
    private OpenLibraryRequester openLibraryRequester;

    @BeforeAll
    void createUser() {
        this.mockUser = MockUser.createOne(password);
        userRepository.save(mockUser);
    }

    @BeforeEach
    void cleanRepositories() {
        repository.deleteAll();
    }

    @Test
    void should_createBook_when_receivesBook() {
        Book mockBook = MockBook.createOne();
        BookDTO bookDTO = BookDtoConverter.convertBookToDto(mockBook);

        BookDTO response = bookConnector.request(
                null,
                HttpMethod.POST,
                port,
                mockUser.getUsername(),
                password,
                bookDTO,
                null
        );

        Book savedBook = repository.findFirstByAuthor(mockBook.getAuthor())
                .orElseThrow();

        BookInfoDTO bookInfoDTO = BookInfoConverter.convertBookDtoToBookInfoDto(bookDTO);
        assertThat(response).isEqualToComparingFieldByField(bookInfoDTO);
        assertThat(savedBook).isEqualToComparingFieldByField(bookDTO);
    }

    @Test
    void should_deleteBook_when_receivesId() {
        Book mockBook = MockBook.createOne();
        repository.save(mockBook);

        bookConnector.deleteReq(
                mockBook.getId(),
                port,
                mockUser.getUsername(),
                password
        );

        Optional<Book> deletedBook = repository.findFirstByAuthor(mockBook.getAuthor());
        if (deletedBook.isPresent()) {
            Assert.fail("The book should not exist");
        }
    }

    @Test
    void should_createBook_when_bookExists_on_externalService() {
        MockOpenLibraryResponse mockOpenLibraryResponse = new MockOpenLibraryResponse();
        OpenLibraryResponse openLibraryResponse = mockOpenLibraryResponse.create();
        String isbn = "000000000";
        Mockito.when(
                openLibraryRequester.getBookByIsbn(isbn)
        ).thenReturn(Optional.of(openLibraryResponse));

        String param = String.format("isbn=%s", isbn);

        bookConnector.request(
                null,
                HttpMethod.GET,
                port,
                mockUser.getUsername(),
                password,
                null,
                param
        );

        Optional<Book> book = repository.findByIsbn(isbn);
        if (book.isEmpty()) {
            Assert.fail("The book should have been created");
        }

    }

    @Test
    void should_getBook_when_bookExists() {
        Book mockBook = MockBook.createOne();
        repository.save(mockBook);

        String isbn = mockBook.getIsbn();
        String param = String.format("isbn=%s", isbn);

        BookInfoDTO response = bookConnector.openLibraryReq(
                port,
                mockUser.getUsername(),
                password,
                param
        );

        BookInfoDTO bookDTO = BookInfoConverter.convertBookToBookInfoDto(mockBook);
        assertThat(bookDTO).isEqualToComparingFieldByField(response);
    }

    @Test
    void should_fail_when_bookDoesNotExist_on_externalService() {
        String isbn = "000000000";
        Mockito.when(
                openLibraryRequester.getBookByIsbn(isbn)
        ).thenThrow(new BookNotFoundException());

        String expectedErrorMessage = String.format(ErrorConstats.NOT_FOUND_MESSAGE, "book");
        String expectedResponseStatus = "404";

        String param = String.format("isbn=%s", isbn);

        try {
            bookConnector.openLibraryReq(
                    port,
                    mockUser.getUsername(),
                    password,
                    param
            );
        } catch (Exception ex) {
            assertThat(ex.getMessage().contains(expectedErrorMessage)).isTrue();
            assertThat(ex.getMessage().contains(expectedResponseStatus)).isTrue();
        }
    }

    @Test
    void should_fail_when_isbnIsEmpty() {
        String param = "isbn=";
        String expectedErrorMessage = "Missing required field";
        String expectedResponseStatus = "400";

        try {
            bookConnector.openLibraryReq(
                    port,
                    mockUser.getUsername(),
                    password,
                    param
            );
        } catch (Exception ex) {
            assertThat(ex.getMessage().contains(expectedErrorMessage)).isTrue();
            assertThat(ex.getMessage().contains(expectedResponseStatus)).isTrue();
        }
    }
}
