package converters;

import wolox.training.dto.BookDTO;
import wolox.training.models.Book;

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
}
