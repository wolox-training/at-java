package wolox.training.controllers;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import wolox.training.mocks.MockBook;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
public class BookControllerSpec {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookRepository repository;

    @Test
    public void onGet_givenAnAuthor_thenReturnsBook () throws Exception {
        Book mockBook = MockBook.createOne();
        given(repository.findFirstByAuthor("Dumas")).willReturn(mockBook);

        mvc.perform(MockMvcRequestBuilders.get("/api/books/Dumas")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre", equalTo(mockBook.getGenre())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author", equalTo(mockBook.getAuthor())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.image", equalTo(mockBook.getImage())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", equalTo(mockBook.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.subtitle", equalTo(mockBook.getSubtitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.publisher", equalTo(mockBook.getPublisher())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.year", equalTo(mockBook.getYear())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pages", equalTo(mockBook.getPages())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn", equalTo(mockBook.getIsbn())));
    }

    @Test
    public void onPost_givenABook_thenCreatesNewBook () throws Exception {
        Book mockBook = MockBook.createOne();
        when(repository.save(mockBook)).thenReturn(mockBook);

        ObjectMapper objectMapper = new ObjectMapper();
        String bookJSON = objectMapper.writeValueAsString(mockBook);
        
        mvc.perform(MockMvcRequestBuilders.post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(bookJSON))
                .andExpect(status().isCreated())
                .andDo(mvcResult -> {
                    String pepe = mvcResult.getResponse().getContentAsString();
                   assert(mvcResult).equals(mockBook);
                });
                /*.andExpect(MockMvcResultMatchers.jsonPath("$.genre", equalTo(mockBook.getGenre())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.author", equalTo(mockBook.getAuthor())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.image", equalTo(mockBook.getImage())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", equalTo(mockBook.getTitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.subtitle", equalTo(mockBook.getSubtitle())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.publisher", equalTo(mockBook.getPublisher())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.year", equalTo(mockBook.getYear())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pages", equalTo(mockBook.getPages())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.isbn", equalTo(mockBook.getIsbn())));*/
    }
}
