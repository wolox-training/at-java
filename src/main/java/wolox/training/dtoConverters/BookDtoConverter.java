package wolox.training.dtoConverters;

import wolox.training.dto.BookDTO;
import wolox.training.models.Book;

public class BookDtoConverter {
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

    public static Book convertExisting(BookDTO bookDTO, Book book) {
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
}
