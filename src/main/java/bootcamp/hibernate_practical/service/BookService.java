package bootcamp.hibernate_practical.service;

import bootcamp.hibernate_practical.dto.BookResponse;
import bootcamp.hibernate_practical.dto.CreateBookRequest;
import bootcamp.hibernate_practical.dto.UpdateBookRequest;
import bootcamp.hibernate_practical.entity.Book;
import bootcamp.hibernate_practical.entity.BookStatus;
import bootcamp.hibernate_practical.exception.BookNotFoundException;
import bootcamp.hibernate_practical.exception.InvalidBookStatusException;
import bootcamp.hibernate_practical.exception.InvalidRequestException;
import bootcamp.hibernate_practical.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookResponse createBook(CreateBookRequest request) {
        Book book = new Book(
                request.getTitle(),
                request.getAuthor(),
                request.getGenre(),
                request.getPublicationYear(),
                BookStatus.AVAILABLE
        );
        Book savedBook = bookRepository.save(book);
        return mapToResponse(savedBook);
    }

    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    public BookResponse getBookById(Long id) {
        return bookRepository.findById(id).map(this::mapToResponse).orElseThrow(
                () -> new BookNotFoundException(id)
        );
    }

    public BookResponse updateBook(Long id, UpdateBookRequest request) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        if(book.getBorrowedStatus()==BookStatus.BORROWED || request.getBorrowedStatus()==BookStatus.BORROWED){
            throw new InvalidBookStatusException("Book " + id + " cannot change status via update while BORROWED or to BORROWED");
        }
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setGenre(request.getGenre());
        book.setPublicationYear(request.getPublicationYear());
        book.setBorrowedStatus(request.getBorrowedStatus());
        Book savedBook = bookRepository.save(book);
        return mapToResponse(savedBook);
    }

    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException(id);
        }
        bookRepository.deleteById(id);
    }

    public void borrowBook(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        if (book.getBorrowedStatus() != BookStatus.AVAILABLE) {
            throw new InvalidBookStatusException("Book " + id + " is not available");
        }
        book.setBorrowedStatus(BookStatus.BORROWED);
        bookRepository.save(book);
    }

    public void returnBook(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        if (book.getBorrowedStatus() != BookStatus.BORROWED) {
            throw new InvalidBookStatusException("Book " + id + " is not borrowed");
        }
        book.setBorrowedStatus(BookStatus.AVAILABLE);
        bookRepository.save(book);
    }

    public List<BookResponse> findByAuthor(String author) {
        if (author == null || author.isBlank()) {
            throw new InvalidRequestException("Author name is required");
        }
        return bookRepository.findByAuthor(author).stream().map(this::mapToResponse).toList();
    }

    public List<BookResponse> findByTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new InvalidRequestException("Title is required");
        }
        return bookRepository.findByTitleContainingIgnoreCase(title).stream().map(this::mapToResponse).toList();
    }

    public List<BookResponse> findAvailableBooks() {
        return bookRepository.findBookByBorrowedStatus(BookStatus.AVAILABLE).stream().map(this::mapToResponse).toList();
    }

    private BookResponse mapToResponse(Book book) {
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getGenre(),
                book.getPublicationYear(),
                book.getBorrowedStatus()
        );
    }
}
