package com.amaurote.social.service;

import com.amaurote.domain.entity.Book;
import com.amaurote.domain.entity.User;
import com.amaurote.domain.entity.UserBookReview;

import java.util.List;

public interface ReviewService {

    List<UserBookReview> getBookReviews(Book book);

    UserBookReview getUserBookReview(Book book, User reviewer);

    void reviewOrUpdate(Book book, User reviewer, String text);
}
