package wolox.training.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import wolox.training.TrainingApplication;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
public class BookControllerSpec {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookRepository repository;

    @Test
    public void givenAnAuthor_thenReturnsBook () throws Exception {
        Book mockBook = new Book();
        mockBook.setAuthor("Dumas");
        mockBook.setGenre("Adventure");
        mockBook.setTitle("The three musketeers");
        mockBook.setSubtitle("");
        mockBook.setImage("http://image.png");
        mockBook.setPublisher("B.B. Editors");
        mockBook.setPages(700);
        mockBook.setIsbn("9781567673067");
        mockBook.setYear("1884");

        given(repository.findFirstByAuthor("Dumas")).willReturn(mockBook);

        mvc.perform(MockMvcRequestBuilders.get("/api/books/Dumas")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", is(mockBook.getTitle())));
    }
}

