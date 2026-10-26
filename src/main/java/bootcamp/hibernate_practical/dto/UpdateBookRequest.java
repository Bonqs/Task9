package bootcamp.hibernate_practical.dto;

import bootcamp.hibernate_practical.entity.BookStatus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateBookRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String author;

    @NotBlank
    private String genre;

    @Min(868)
    @Max(2026)
    private int publicationYear;

    @NotNull
    private BookStatus borrowedStatus;
}
