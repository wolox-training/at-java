package wolox.training.controllers;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import wolox.training.dto.BookDTO;
import wolox.training.dtoConverters.BookDtoConverter;
import wolox.training.exceptions.BookIdMismatchException;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.exceptions.ErrorConstats;
import wolox.training.mocks.InvalidBook;
import wolox.training.mocks.MockBook;
import wolox.training.models.Book;
import wolox.training.services.BookService;
import wolox.training.services.OpenLibraryService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookControllerSpec {
    @MockBean
    private BookService bookService;

    @MockBean
    private OpenLibraryService openLibraryService;

    @Test
    public void should_getBook_when_authorReceived () throws Exception {
        BookController bookController = new BookController(bookService, openLibraryService);
        Book mockBook = MockBook.createOne();
        BookDTO bookDTO = BookDtoConverter.convertBookToDto(mockBook);
        Mockito.when(bookService.findByAuthor("Dumas")).thenReturn(bookDTO);

        BookDTO result = bookController.findByAuthor("Dumas");
        Assert.assertEquals(bookDTO, result);
    }

    @WithMockUser()
    @Test
    public void should_failToGetBook_when_authorBookDoesNotExist () throws Exception {
        BookController bookController = new BookController(bookService, openLibraryService);
        Mockito.when(bookService.findByAuthor("Dumas")).thenThrow(new BookNotFoundException());

        try {
            bookController.findByAuthor("Dumas");
        } catch (BookNotFoundException ignored) {
        } catch (Exception ex) {
            Assert.fail();
        }
        Assert.fail();
    }

    @WithMockUser()
    @Test
    public void should_createBook_when_receivesBook () throws Exception {
        BookController bookController = new BookController(bookService, openLibraryService);
        Book mockBook = MockBook.createOne();
        BookDTO bookDTO = BookDtoConverter.convertBookToDto(mockBook);

        Mockito.when(bookService.createBook(bookDTO)).thenReturn(bookDTO);

        BookDTO result = bookController.create(bookDTO);
        Assert.assertEquals(bookDTO, result);
    }

    @Test
    @WithMockUser()
    public void should_updateBook_when_recievesBookToUpdate () throws Exception {
        BookController bookController = new BookController(bookService, openLibraryService);
        Long bookId = 1L;
        Book mockBook = MockBook.createOneWithId(bookId);
        BookDTO bookDTO = BookDtoConverter.convertBookToDto(mockBook);

        Mockito.when(bookService.bookExistsById(bookId)).thenReturn(true);
        Mockito.when(bookService.updateBook(bookDTO)).thenReturn(bookDTO);
        Mockito.when(bookService.findById(bookId)).thenReturn(bookDTO);

        BookDTO result = bookController.update(bookDTO, bookId);

        Assert.assertEquals(bookDTO, result);
    }

    @Test
    @WithMockUser()
    public void should_createBook_when_recievesBookToUpdateThatDoesNotExist () throws Exception {
        BookController bookController = new BookController(bookService, openLibraryService);
        Long bookId = 1L;
        Book mockBook = MockBook.createOneWithId(bookId);

        BookDTO bookDTO = BookDtoConverter.convertBookToDto(mockBook);

        Mockito.when(bookService.bookExistsById(bookId)).thenReturn(false);
        Mockito.when(bookService.createBook(bookDTO)).thenReturn(bookDTO);
        Mockito.when(bookService.findById(bookId)).thenReturn(bookDTO);

        BookDTO result = bookController.update(bookDTO, bookId);

        Assert.assertEquals(bookDTO, result);
    }

    @WithMockUser()
    @Test
    public void should_failToCreateBook_when_bookHasNoAuthor () throws Exception {
        BookController bookController = new BookController(bookService, openLibraryService);
        InvalidBook noAuthorBook = InvalidBook.bookWithoutAuthor();
        BookDTO bookDTO = BookDtoConverter.convertBookToDto(noAuthorBook);
        String errorMessage = String.format(ErrorConstats.FIELD_CANNOT_BE_EMPTY, "author");
        Mockito.when(bookService.createBook(bookDTO))
                .thenThrow(new IllegalArgumentException(errorMessage));

        try {
            bookController.create(bookDTO);
        } catch (IllegalArgumentException ignored) {
        } catch (Exception ex){
            Assert.assertEquals(ex.getMessage(), errorMessage);
            Assert.fail();
        }
        Assert.fail();
    }

    @WithMockUser()
    @Test
    void should_failToUpdateBook_when_bookIdDoesNotMatchRequestedId () throws Exception {
        BookController bookController = new BookController(bookService, openLibraryService);
        Long bookId = 1L;
        Book mockBook = MockBook.createOneWithId(bookId + 1);
        BookDTO bookDTO = BookDtoConverter.convertBookToDto(mockBook);

        try {
            bookController.update(bookDTO, bookId);
        } catch (BookIdMismatchException ignored) {
            System.out.println("pepe");
            return;
        } catch (Exception ex){
            Assert.fail("Should have failed with BookIdMismatchException");
        }

        Assert.fail("Should have failed with BookIdMismatchException");

    }
}
