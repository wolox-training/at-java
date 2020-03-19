package wolox.training.converters;

import java.util.stream.Collectors;
import wolox.training.dto.BookDTO;
import wolox.training.models.Book;
import wolox.training.services.connectors.dtos.Author;
import wolox.training.services.connectors.dtos.OpenLibraryResponse;
import wolox.training.services.connectors.dtos.Publisher;
import wolox.training.services.connectors.dtos.Subject;

public class BookConverter {
    public static Book convert(BookDTO bookDTO) {
        Book book = new Book();
        book.setGenre(bookDTO.getGenre());
        book.setAuthor(bookDTO.getAuthor());
        book.setTitle(bookDTO.getTitle());
        book.setSubtitle(bookDTO.getSubtitle());
        book.setImage(bookDTO.getImage());
        book.setPublisher(bookDTO.getPublisher());
        book.setPages(bookDTO.getPages());
        book.setIsbn(bookDTO.getIsbn());
        book.setYear(bookDTO.getYear());

        return book;
    }

    public static Book convertResponseToBook(OpenLibraryResponse openLibraryResponse, String isbn) {
        Book book = new Book();
        book.setTitle(openLibraryResponse.getTitle());
        book.setSubtitle(openLibraryResponse.getSubtitle());
        book.setYear(openLibraryResponse.getPublishDate());
        book.setIsbn(isbn);
        book.setPages(openLibraryResponse.getPages());

        String publisher = openLibraryResponse
                .getPublishers()
                .stream()
                .map(Publisher::getName)
                .collect(Collectors.joining(" | "));

        book.setPublisher(publisher);

        String genre = openLibraryResponse
                .getSubjects()
                .stream()
                .map(Subject::getName)
                .collect(Collectors.joining(" | "));
        book.setGenre(genre);

        String author = openLibraryResponse
                .getAuthors()
                .stream()
                .map(Author::getName)
                .collect(Collectors.joining(" | "));
        book.setAuthor(author);

        book.setImage(openLibraryResponse.getCover().getLarge());

        return book;
    }
}
