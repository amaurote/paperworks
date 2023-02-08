package com.amaurote.social.service;

import com.amaurote.domain.entity.Book;
import com.amaurote.domain.entity.User;
import com.amaurote.domain.entity.UserBookReview;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public interface ReviewService {

    List<UserBookReview> getBookReviews(Book book); // todo pagination

    UserBookReview getUserBookReview(Book book, User reviewer);

    void reviewOrUpdate(Book book, User reviewer, String text);

    void deleteReview(Book book, User reviewer);

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class UserReviewRequestDTO {
        @NotBlank
        private String book;
        @NotBlank
        @Size(min = 10, max = 500)
        private String text;
    }
}
