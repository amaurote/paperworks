package com.amaurote.social.service;

import com.amaurote.domain.entity.Book;
import com.amaurote.domain.entity.User;
import com.amaurote.social.dto.RatingAggregateResults;
import com.amaurote.social.exception.SocialServiceException;

public interface RatingService {

    RatingAggregateResults getBookRating(Book book);

    Integer getUserBookRating(Book book, User reviewer);

    void rateOrUpdate(Book book, User reviewer, int score) throws SocialServiceException;

    void deleteRating(Book book, User reviewer);

}
