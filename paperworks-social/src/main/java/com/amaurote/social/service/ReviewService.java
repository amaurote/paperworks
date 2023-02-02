package com.amaurote.social.service;

import com.amaurote.domain.entity.Book;
import com.amaurote.domain.entity.User;
import com.amaurote.domain.entity.UserBookReview;
import com.amaurote.social.exception.SocialServiceException;

import java.util.List;

public interface ReviewService {

    List<UserBookReview> getBookReviews(Book book);

    UserBookReview getUserBookReview(Book book, User reviewer);

    UserBookReview getUserBookReviewById(long id) throws SocialServiceException;

    void reviewOrUpdate(Book book, User reviewer, String text);

    void deleteReview(UserBookReview review);
}
