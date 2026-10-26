package bootcamp.hibernate_practical.dto;

import bootcamp.hibernate_practical.entity.BookStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BookResponse {
    private Long id;
    private String title;
    private String author;
    private String genre;
    private int publicationYear;
    private BookStatus borrowedStatus;
}
