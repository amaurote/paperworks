package com.amaurote.social.service.impl;

import com.amaurote.domain.entity.Book;
import com.amaurote.domain.entity.User;
import com.amaurote.domain.entity.UserBookRating;
import com.amaurote.social.dto.RatingAggregateResults;
import com.amaurote.social.exception.SocialServiceException;
import com.amaurote.social.repository.UserBookRatingRepository;
import com.amaurote.social.service.RatingService;
import org.springframework.stereotype.Service;

@Service
public record RatingServiceImpl(UserBookRatingRepository ratingRepository) implements RatingService {

    @Override
    public RatingAggregateResults getBookRating(Book book) {
        return ratingRepository.getBookRating(book);
    }

    @Override
    public Integer getUserBookRating(Book book, User reviewer) {
        return ratingRepository.findByBookAndReviewer(book, reviewer)
                .map(UserBookRating::getScore).orElse(null);
    }

    @Override
    public void rateOrUpdate(Book book, User reviewer, int score) throws SocialServiceException {
        if (book == null || reviewer == null)
            throw new SocialServiceException("Unable to map rating");
        if (score < 1 || score > 5)
            throw new SocialServiceException("Invalid rating value");

        var rating = ratingRepository.findByBookAndReviewer(book, reviewer).orElse(new UserBookRating());
        rating.setReviewer(reviewer);
        rating.setBook(book);
        rating.setScore(score);
        ratingRepository.save(rating);
    }

    @Override
    public void deleteRating(Book book, User reviewer) {
        ratingRepository.findByBookAndReviewer(book, reviewer).ifPresent(ratingRepository::delete);
    }

}
