package com.amaurote.social.service.impl;

import com.amaurote.domain.entity.Book;
import com.amaurote.domain.entity.User;
import com.amaurote.domain.entity.UserBookReview;
import com.amaurote.social.repository.UserBookReviewRepository;
import com.amaurote.social.service.ReviewService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public record ReviewServiceImpl(UserBookReviewRepository reviewRepository) implements ReviewService {

    @Override
    public List<UserBookReview> getBookReviews(Book book) {
        return reviewRepository.findAllByBook(book);
    }

    @Override
    public UserBookReview getUserBookReview(Book book, User reviewer) {
        return reviewRepository.findByBookAndReviewer(book, reviewer).orElse(null);
    }

    @Override
    public void reviewOrUpdate(Book book, User reviewer, String text) {
        var existing = reviewRepository.findByBookAndReviewer(book, reviewer).orElse(null);
        if (existing != null) {
            existing.setText(text);
            existing.setDateUpdated(Instant.now());
            reviewRepository.save(existing);
        } else {
            var newReview = UserBookReview.builder()
                    .book(book)
                    .reviewer(reviewer)
                    .text(text)
                    .dateCreated(Instant.now())
                    .build();
            reviewRepository.save(newReview);
        }
    }

    @Override
    public void deleteReview(Book book, User reviewer) {
        reviewRepository.deleteByBookAndReviewer(book, reviewer);
    }
}
