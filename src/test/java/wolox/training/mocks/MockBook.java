package wolox.training.mocks;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import wolox.training.dto.BookDTO;
import wolox.training.models.Book;

public class MockBook {
    static public Book createOne(){
        Book mockBook = new Book();

        mockBook.setAuthor("Dumas");
        mockBook.setGenre("Adventure");
        mockBook.setTitle("The three musketeers");
        mockBook.setSubtitle("");
        mockBook.setImage("http://image.png");
        mockBook.setPublisher("A. Editors");
        mockBook.setPages(700);
        mockBook.setIsbn("9781567673067");
        mockBook.setYear("1884");

        return mockBook;
    }

    static public Book createOneWithId(Long id) throws NoSuchFieldException, IllegalAccessException{
        Book mockBook = new Book();
        Field fieldId = mockBook.getClass().getDeclaredField("id");
        fieldId.setAccessible(true);
        fieldId.set(mockBook, id);

        mockBook.setAuthor("Dumas");
        mockBook.setGenre("Adventure");
        mockBook.setTitle("The three musketeers");
        mockBook.setSubtitle("");
        mockBook.setImage("http://image.png");
        mockBook.setPublisher("A. Editors");
        mockBook.setPages(700);
        mockBook.setIsbn("9781567673067");
        mockBook.setYear("1884");

        return mockBook;
    }

    static public List<Book> createMany() {
        Book mockBook_1 = new Book();
        mockBook_1.setAuthor("Dumas");
        mockBook_1.setGenre("Adventure");
        mockBook_1.setTitle("The three musketeers");
        mockBook_1.setSubtitle("");
        mockBook_1.setImage("http://image.png");
        mockBook_1.setPublisher("A. Editors");
        mockBook_1.setPages(700);
        mockBook_1.setIsbn("9781567673067");
        mockBook_1.setYear("1884");

        Book mockBook_2 = new Book();
        mockBook_2.setAuthor("Ende");
        mockBook_2.setGenre("Adventure");
        mockBook_2.setTitle("The neverending story");
        mockBook_2.setSubtitle("");
        mockBook_2.setImage("http://image.png");
        mockBook_2.setPublisher("B.B.B. Editors");
        mockBook_2.setPages(700);
        mockBook_2.setIsbn("9781567673067");
        mockBook_2.setYear("1884");

        List<Book> mockBookList = new ArrayList<>();
        mockBookList.add(mockBook_1);
        mockBookList.add(mockBook_2);

        return mockBookList;
    }
}
