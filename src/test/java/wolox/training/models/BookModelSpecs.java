package wolox.training.models;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;
import wolox.training.mocks.MockBook;
import wolox.training.repositories.BookRepository;

@DataJpaTest
@RunWith(SpringRunner.class)
public class BookModelSpecs {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BookRepository repository;
    @Test
    void should_updateBook_when_recievesBookToUpdate () throws Exception {
        Book mockBook = MockBook.createOne();
        repository.save(mockBook);

        assertThat(repository.findById(mockBook.getId())).isPresent();

        Optional<Book> updatedBook = repository.findById(mockBook.getId());
        assertThat(updatedBook).isEqualTo(Optional.of(mockBook));
    }

    @Test void should_failToCreateBook_when_bookHasNoAuthor () throws Exception {
        Book book = new Book();

        try {
            repository.save(book);
            repository.flush();
        } catch (DataIntegrityViolationException ex) {
            return;
        }

        Assert.fail();
    }
}

