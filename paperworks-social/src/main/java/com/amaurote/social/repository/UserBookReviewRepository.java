package com.amaurote.social.repository;

import com.amaurote.domain.entity.Book;
import com.amaurote.domain.entity.User;
import com.amaurote.domain.entity.UserBookReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserBookReviewRepository extends JpaRepository<UserBookReview, Long> {
    Optional<UserBookReview> findByBookAndReviewer(Book book, User reviewer);
}
