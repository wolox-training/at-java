package wolox.training.dtoConverters;

import wolox.training.dto.BookDTO;
import wolox.training.models.Book;

public class BookDtoConverter {
    public static Book convertDtoToBook(BookDTO bookDTO) {
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

    public static Book convertDtoToExistingBook(BookDTO bookDTO, Book book) {
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

    public static BookDTO convertBookToDto(Book book) {
        BookDTO bookDTO = new BookDTO();

        bookDTO.setId(book.getId());
        bookDTO.setGenre(book.getGenre());
        bookDTO.setAuthor(book.getAuthor());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setSubtitle(book.getSubtitle());
        bookDTO.setImage(book.getImage());
        bookDTO.setPublisher(book.getPublisher());
        bookDTO.setPages(book.getPages());
        bookDTO.setIsbn(book.getIsbn());
        bookDTO.setYear(book.getYear());

        return bookDTO;
    }
}
