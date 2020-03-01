package wolox.training.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import wolox.training.mocks.MockBook;
import wolox.training.mocks.InvalidBook;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import wolox.training.utils.ResponseBodyMatchers;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
public class BookControllerSpec {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookRepository repository;

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

    @Test
    public void should_failToGetBook_when_authorBookDoesNotExist () throws Exception {
        Mockito.when(repository.findFirstByAuthor("Dumas")).thenReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders.get("/api/books/Dumas")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void should_createBook_when_receivesBook () throws Exception {
        Book mockBook = MockBook.createOne();
        Mockito.when(repository.save(mockBook)).thenReturn(mockBook);

        ObjectMapper objectMapper = new ObjectMapper();
        String bookJSON = objectMapper.writeValueAsString(mockBook);

        mvc.perform(MockMvcRequestBuilders.post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookJSON))
                .andExpect(status().isCreated())
                .andExpect(
                        ResponseBodyMatchers
                                .responseBody()
                                .containsObjectAsJson(mockBook, Book.class)
                );
    }

    @Test
    public void should_failToCreateBook_when_bookHasNoAuthor () throws Exception {
        InvalidBook invalidBook = new InvalidBook();
        InvalidBook noAuthorBook = invalidBook.bookWithoutAuthor();

        ObjectMapper objectMapper = new ObjectMapper();
        String bookJSON = objectMapper.writeValueAsString(noAuthorBook);

        mvc.perform(MockMvcRequestBuilders.post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookJSON))
                .andExpect(status().isBadRequest());
    }

    @Test void should_updateBook_when_recievesBookToUpdate () throws Exception {
        Book mockBook = MockBook.createOne();
        Mockito.when(repository.existsById(mockBook.getId())).thenReturn(true);
        Mockito.when(repository.save(mockBook)).thenReturn(mockBook);

        ObjectMapper objectMapper = new ObjectMapper();
        String bookJSON = objectMapper.writeValueAsString(mockBook);

        mvc.perform(MockMvcRequestBuilders.put("/api/books/" + mockBook.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookJSON))
                .andExpect(status().isOk())
                .andExpect(
                        ResponseBodyMatchers
                                .responseBody()
                                .containsObjectAsJson(mockBook, Book.class)
                );
    }

    @Test void should_failToUpdateBook_when_bookIdDoesNotMatchRequestedId () throws Exception {
        Book mockBook = MockBook.createOne();

        ObjectMapper objectMapper = new ObjectMapper();
        String bookJSON = objectMapper.writeValueAsString(mockBook);

        mvc.perform(MockMvcRequestBuilders.put("/api/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookJSON))
                .andExpect(status().isBadRequest());
    }
}
