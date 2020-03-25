package wolox.training.integration;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import wolox.training.controllers.BookController;
import wolox.training.mocks.MockBook;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;
import wolox.training.utils.ResponseBodyMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BookIntegrationTests {
    @Autowired
    BookRepository repository;

    @Autowired
    private MockMvc mvc;

    @WithMockUser()
    @Test
    void should_updateBook_when_recievesBookToUpdate () throws Exception {
        Book mockBook = MockBook.createOne();
        repository.save(mockBook);

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

    @Test
    void should_failToUpdateBook_when_UserIsNotAuthenticated () throws Exception {
        mvc.perform(MockMvcRequestBuilders.put("/api/books/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
}
