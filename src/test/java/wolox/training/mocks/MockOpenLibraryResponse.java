package wolox.training.mocks;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import wolox.training.services.connectors.dtos.Author;
import wolox.training.services.connectors.dtos.Cover;
import wolox.training.services.connectors.dtos.OpenLibraryResponse;
import wolox.training.services.connectors.dtos.Publisher;
import wolox.training.services.connectors.dtos.Subject;

public class MockOpenLibraryResponse {

    public OpenLibraryResponse create() {
        OpenLibraryResponse openLibraryResponse = new OpenLibraryResponse();

        openLibraryResponse.setTitle("Title");
        openLibraryResponse.setSubtitle("Subtitle");

        Publisher publisher = new Publisher();
        publisher.setName("Some Editorial");
        List<Publisher> publishers = new ArrayList<>();
        publishers.add(publisher);
        openLibraryResponse.setPublishers(publishers);

        openLibraryResponse.setPublishDate("1985");
        openLibraryResponse.setPages(203);

        Author author = new Author();
        List<Author> authors = new ArrayList<>();
        authors.add(author);
        openLibraryResponse.setAuthors(authors);

        Subject subject = new Subject();
        List<Subject> subjects = new ArrayList<>();
        subjects.add(subject);
        openLibraryResponse.setSubjects(subjects);

        Cover cover = new Cover();
        cover.setLarge("http://photo.png");
        openLibraryResponse.setCover(cover);

        return openLibraryResponse;
    }
}
