package wolox.training.controllers;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import wolox.training.dto.BookDTO;
import wolox.training.dtoConverters.BookDtoConverter;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.exceptions.ErrorConstats;
import wolox.training.exceptions.RequiredArgumentException;
import wolox.training.mocks.InvalidBook;
import wolox.training.mocks.MockBook;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;
import wolox.training.repositories.UserRepository;
import wolox.training.services.BookService;
import wolox.training.services.OpenLibraryService;
import wolox.training.utils.ResponseBodyMatchers;
import wolox.training.utils.RestUtils;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
@AutoConfigureMockMvc
public class BookControllerSpec {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private BookRepository repository;

    @MockBean
    private BookService bookService;

    @MockBean
    private OpenLibraryService openLibraryService;

    @WithMockUser()
    @Test
    public void should_getBook_when_authorReceived () throws Exception {
        Book mockBook = MockBook.createOne();
        BookDTO bookDTO = BookDtoConverter.convertBookToDto(mockBook);
        Mockito.when(bookService.findByAuthor("Dumas")).thenReturn(bookDTO);

        mvc.perform(MockMvcRequestBuilders.get("/api/books/Dumas")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(
                        ResponseBodyMatchers
                                .responseBody()
                                .containsObjectAsJson(mockBook, Book.class)
                );
    }

    @WithMockUser()
    @Test
    public void should_failToGetBook_when_authorBookDoesNotExist () throws Exception {
        Mockito.when(bookService.findByAuthor("Dumas")).thenThrow(new BookNotFoundException());

        mvc.perform(MockMvcRequestBuilders.get("/api/books/Dumas")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @WithMockUser()
    @Test
    public void should_createBook_when_receivesBook () throws Exception {
        Book mockBook = MockBook.createOne();
        BookDTO bookDTO = BookDtoConverter.convertBookToDto(mockBook);

        Mockito.when(bookService.createBook(bookDTO)).thenReturn(bookDTO);

        mvc.perform(MockMvcRequestBuilders.post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(RestUtils.ObjectToStringJSON(mockBook)))
                .andExpect(status().isCreated())
                .andExpect(
                        ResponseBodyMatchers
                                .responseBody()
                                .containsObjectAsJson(bookDTO, BookDTO.class)
                );
    }

    @Test
    @WithMockUser()
    public void should_updateBook_when_recievesBookToUpdate () throws Exception {
        Long bookId = 1L;
        Book mockBook = MockBook.createOneWithId(bookId);

        BookDTO bookDTO = BookDtoConverter.convertBookToDto(mockBook);

        Mockito.when(bookService.bookExistsById(bookId)).thenReturn(true);
        Mockito.when(bookService.updateBook(bookDTO)).thenReturn(bookDTO);
        Mockito.when(bookService.findById(bookId)).thenReturn(bookDTO);

        mvc.perform(MockMvcRequestBuilders.put("/api/books/" + bookId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(RestUtils.ObjectToStringJSON(bookDTO)))
                .andExpect(status().isOk())
                .andExpect(
                        ResponseBodyMatchers
                                .responseBody()
                                .containsObjectAsJson(bookDTO, BookDTO.class)
                );
    }

    @Test
    @WithMockUser()
    public void should_createBook_when_recievesBookToUpdateThatDoesNotExist () throws Exception {
        Long bookId = 1L;
        Book mockBook = MockBook.createOneWithId(bookId);

        BookDTO bookDTO = BookDtoConverter.convertBookToDto(mockBook);

        Mockito.when(bookService.bookExistsById(bookId)).thenReturn(false);
        Mockito.when(bookService.createBook(bookDTO)).thenReturn(bookDTO);
        Mockito.when(bookService.findById(bookId)).thenReturn(bookDTO);

        mvc.perform(MockMvcRequestBuilders.put("/api/books/" + bookId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(RestUtils.ObjectToStringJSON(bookDTO)))
                .andExpect(status().isOk())
                .andExpect(
                        ResponseBodyMatchers
                                .responseBody()
                                .containsObjectAsJson(bookDTO, BookDTO.class)
                );
    }

    @WithMockUser()
    @Test
    public void should_failToCreateBook_when_bookHasNoAuthor () throws Exception {
        InvalidBook noAuthorBook = InvalidBook.bookWithoutAuthor();
        BookDTO bookDTO = BookDtoConverter.convertBookToDto(noAuthorBook);
        Mockito.when(bookService.createBook(bookDTO))
                .thenThrow(new IllegalArgumentException("The field author cannot be empty."));

        mvc.perform(MockMvcRequestBuilders.post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(RestUtils.ObjectToStringJSON(noAuthorBook)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(
                        containsString(
                                String.format(ErrorConstats.FIELD_CANNOT_BE_EMPTY, "author")
                        ))
                );
    }

    @WithMockUser()
    @Test
    void should_failToUpdateBook_when_bookIdDoesNotMatchRequestedId () throws Exception {
        Long bookId = 1L;
        Book mockBook = MockBook.createOneWithId(bookId);
        BookDTO bookDTO = BookDtoConverter.convertBookToDto(mockBook);

        mvc.perform(MockMvcRequestBuilders.put("/api/books/"+ bookId +1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(RestUtils.ObjectToStringJSON(bookDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(
                        containsString(
                                String.format(ErrorConstats.ID_MISSMATCH_MESSAGE, "book")
                        ))
                );
    }

    @Test
    void should_failToUpdateBook_when_UserIsNotAuthenticated () throws Exception {
        mvc.perform(MockMvcRequestBuilders.put("/api/books/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
}
