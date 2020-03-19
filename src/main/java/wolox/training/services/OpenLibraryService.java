package wolox.training.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wolox.training.converters.BookConverter;
import wolox.training.converters.BookInfoConverter;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.exceptions.RequestException;
import wolox.training.services.connectors.dtos.OpenLibraryResponse;
import wolox.training.services.dtos.BookInfoDTO;
import wolox.training.models.Book;
import wolox.training.services.connectors.OpenLibraryRequester;

@Service
public class OpenLibraryService {
    @Autowired
    BookService bookService;

    @Autowired
    OpenLibraryRequester openLibraryRequest;

    public BookInfoDTO bookInfo(String isbn) {
        OpenLibraryResponse openLibraryResponse = openLibraryRequest.getBookByIsbn(isbn)
                .orElseThrow(BookNotFoundException::new);

        try {
            Book book = BookConverter.convertResponseToBook(openLibraryResponse, isbn);
            bookService.createBook(book);

            return BookInfoConverter
                    .convertResponseToBookInfoDto(openLibraryResponse, isbn, book.getId());

        } catch (Exception ex) {
            throw new RequestException(ex.getLocalizedMessage());
        }
    }
}
