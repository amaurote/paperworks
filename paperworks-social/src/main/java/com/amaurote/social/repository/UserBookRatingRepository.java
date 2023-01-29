package com.amaurote.social.repository;

import com.amaurote.domain.entity.Book;
import com.amaurote.domain.entity.User;
import com.amaurote.domain.entity.UserBookRating;
import com.amaurote.social.dto.RatingAggregateResults;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserBookRatingRepository extends JpaRepository<UserBookRating, Long> {

    @Query("select new com.amaurote.social.dto.RatingAggregateResults(avg(r.score), count(r.score)) " +
            "from UserBookRating r where r.book = :book")
    RatingAggregateResults getBookRating(@Param("book") Book book);

    Optional<UserBookRating> findByBookAndReviewer(Book book, User reviewer);
}
