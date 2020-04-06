package wolox.training.converters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import wolox.training.dto.BookDTO;
import wolox.training.models.Book;
import wolox.training.services.connectors.dtos.Author;
import wolox.training.services.connectors.dtos.OpenLibraryResponse;
import wolox.training.services.dtos.BookInfoDTO;
import wolox.training.services.connectors.dtos.Publisher;

public class BookInfoConverter {

    public static BookInfoDTO convertBookDtoToBookInfoDto (BookDTO bookDTO) {
        BookInfoDTO bookInfoDTO = new BookInfoDTO();

        String[] authors = bookDTO.getAuthor().split("\\|");
        ArrayList<String> authorList = new ArrayList<>(Arrays.asList(authors));
        bookInfoDTO.setAuthors(authorList);

        String[] publishers = bookDTO.getPublisher().split("\\|");
        ArrayList<String> publishersList = new ArrayList<>(Arrays.asList(publishers));
        bookInfoDTO.setPublishers(publishersList);

        bookInfoDTO.setId(bookDTO.getId());
        bookInfoDTO.setPublishDate(bookDTO.getYear());
        bookInfoDTO.setTitle(bookDTO.getTitle());
        bookInfoDTO.setSubtitle(bookDTO.getSubtitle());
        bookInfoDTO.setIsbn(bookDTO.getIsbn());
        bookInfoDTO.setPages(bookDTO.getPages());

        return bookInfoDTO;
    }

    public static BookInfoDTO convertResponseToBookInfoDto(
            OpenLibraryResponse openLibraryResponse,
            String isbn,
            Long id
    ) {
        BookInfoDTO bookInfoDTO = new BookInfoDTO();

        bookInfoDTO.setId(id);
        bookInfoDTO.setIsbn(isbn);
        bookInfoDTO.setTitle(openLibraryResponse.getTitle());
        bookInfoDTO.setSubtitle(openLibraryResponse.getSubtitle());
        bookInfoDTO.setPublishDate(openLibraryResponse.getPublishDate());
        bookInfoDTO.setPages(openLibraryResponse.getPages());

        List<Publisher> publishersList = openLibraryResponse.getPublishers();
        List<String> publishers = publishersList.stream()
                .map(Publisher::getName)
                .collect(Collectors.toList());
        bookInfoDTO.setPublishers(publishers);

        List<Author> authorsArray = openLibraryResponse.getAuthors();
        List<String> authors = authorsArray.stream()
                .map(Author::getName)
                .collect(Collectors.toList());
        bookInfoDTO.setAuthors(authors);

        return bookInfoDTO;
    }

    public static BookInfoDTO convertBookToBookInfoDto(Book book) {
        BookInfoDTO bookInfoDTO = new BookInfoDTO();

        String[] authors = book.getAuthor().split("\\|");
        ArrayList<String> authorList = new ArrayList<>(Arrays.asList(authors));
        bookInfoDTO.setAuthors(authorList);

        String[] publishers = book.getPublisher().split("\\|");
        ArrayList<String> publishersList = new ArrayList<>(Arrays.asList(publishers));
        bookInfoDTO.setPublishers(publishersList);

        bookInfoDTO.setId(book.getId());
        bookInfoDTO.setPublishDate(book.getYear());
        bookInfoDTO.setTitle(book.getTitle());
        bookInfoDTO.setSubtitle(book.getSubtitle());
        bookInfoDTO.setIsbn(book.getIsbn());
        bookInfoDTO.setPages(book.getPages());

        return bookInfoDTO;
    }
}
