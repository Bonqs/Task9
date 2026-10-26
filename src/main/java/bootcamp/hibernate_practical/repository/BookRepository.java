package bootcamp.hibernate_practical.repository;

import bootcamp.hibernate_practical.entity.Book;
import bootcamp.hibernate_practical.entity.BookStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByAuthor(String author);

    List<Book> findBookByBorrowedStatus(BookStatus status);

    List<Book> findByTitleContainingIgnoreCase(String title);
}
