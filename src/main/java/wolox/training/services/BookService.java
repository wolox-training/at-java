package wolox.training.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wolox.training.dto.BookDTO;
import wolox.training.dtoConverters.BookDtoConverter;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;

@Service
public class BookService {
    @Autowired
    BookRepository bookRepository;

    public BookDTO findByAuthor(String author) {
        Book book = bookRepository
                .findFirstByAuthor(author)
                .orElseThrow(BookNotFoundException::new);

        return BookDtoConverter.convertBookToDto(book);
    }

    public BookDTO findById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);

        return BookDtoConverter.convertBookToDto(book);
    }

    public boolean bookExistsById(Long id) {
        return bookRepository.existsById(id);
    }

    public BookDTO createBook(BookDTO bookDTO) {
        Book book = BookDtoConverter.convertDtoToBook(bookDTO);
        bookRepository.save(book);
        return BookDtoConverter.convertBookToDto(book);
    }

    public BookDTO updateBook(BookDTO bookDTO) {
        Book bookToUpdate = bookRepository.findById(bookDTO.getId())
                .orElseThrow(BookNotFoundException::new);

        Book book = BookDtoConverter.convertDtoToExistingBook(bookDTO, bookToUpdate);
        bookRepository.save(book);
        return BookDtoConverter.convertBookToDto(book);
    }

    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException();
        }

        bookRepository.deleteById(id);
    }
}
