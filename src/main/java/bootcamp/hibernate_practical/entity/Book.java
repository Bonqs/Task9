package bootcamp.hibernate_practical.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    private String genre;
    private int publicationYear;

    @Enumerated(EnumType.STRING)
    private BookStatus borrowedStatus;


    public Book(String title, String author, String genre, int publicationYear, BookStatus borrowedStatus) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publicationYear = publicationYear;
        this.borrowedStatus = borrowedStatus;
    }
}
