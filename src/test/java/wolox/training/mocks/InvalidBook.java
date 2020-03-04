package wolox.training.mocks;

import wolox.training.models.Book;

public class InvalidBook extends Book {
    public void setId(Long id) { this.id = id; }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public static InvalidBook bookWithoutAuthor () {
        InvalidBook book = new InvalidBook();
        book.setId(1L);
        book.setGenre("ScyFy");
        book.setImage("http://image.png");
        book.setTitle("Ubik");
        book.setSubtitle("");
        book.setPublisher("Doubleday");
        book.setYear("1969");
        book.setPages(202);
        book.setIsbn("978-0-575-07921-2");
        return book;
    }
}
