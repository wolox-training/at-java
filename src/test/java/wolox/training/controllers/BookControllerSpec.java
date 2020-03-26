package wolox.training.controllers;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;
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
import wolox.training.exceptions.ErrorConstats;
import wolox.training.mocks.InvalidBook;
import wolox.training.mocks.MockBook;
import wolox.training.mocks.MockUser;
import wolox.training.models.Book;
import wolox.training.models.User;
import wolox.training.repositories.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import wolox.training.repositories.UserRepository;
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

    @WithMockUser()
    @Test
    public void should_getBook_when_authorReceived () throws Exception {
        Book mockBook = MockBook.createOne();
        Mockito.when(repository.findFirstByAuthor("Dumas")).thenReturn(Optional.of(mockBook));

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
        Mockito.when(repository.findFirstByAuthor("Dumas")).thenReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders.get("/api/books/Dumas")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @WithMockUser()
    @Test
    public void should_createBook_when_receivesBook () throws Exception {
        Book mockBook = MockBook.createOne();
        Mockito.when(repository.save(mockBook)).thenReturn(mockBook);

        mvc.perform(MockMvcRequestBuilders.post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(RestUtils.ObjectToStringJSON(mockBook)))
                .andExpect(status().isCreated())
                .andExpect(
                        ResponseBodyMatchers
                                .responseBody()
                                .containsObjectAsJson(mockBook, Book.class)
                );
    }

    @Test
    @WithMockUser()
    public void should_updateBook_when_recievesBookToUpdate () throws Exception {
        Long bookId = 1L;
        Book mockBook = MockBook.createOneWithId(bookId);
        User mockUser = MockUser.createOne();

        Mockito.when(repository.existsById(bookId)).thenReturn(true);
        Mockito.when(repository.save(mockBook)).thenReturn(mockBook);
        Mockito.when(repository.findById(bookId)).thenReturn(Optional.of(mockBook));

        mvc.perform(MockMvcRequestBuilders.put("/api/books/" + bookId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(RestUtils.ObjectToStringJSON(mockBook)))
                .andExpect(status().isOk())
                .andExpect(
                        ResponseBodyMatchers
                                .responseBody()
                                .containsObjectAsJson(mockBook, Book.class)
                );
    }

    @WithMockUser()
    @Test
    public void should_failToCreateBook_when_bookHasNoAuthor () throws Exception {
        InvalidBook noAuthorBook = InvalidBook.bookWithoutAuthor();

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

        Mockito.when(repository.findById(bookId)).thenReturn(Optional.of(mockBook));

        mvc.perform(MockMvcRequestBuilders.put("/api/books/"+ bookId +1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(RestUtils.ObjectToStringJSON(mockBook)))
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
