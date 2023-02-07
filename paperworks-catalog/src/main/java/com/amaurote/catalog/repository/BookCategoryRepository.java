package com.amaurote.catalog.repository;

import com.amaurote.domain.entity.Book;
import com.amaurote.domain.entity.BookCategory;
import com.amaurote.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookCategoryRepository extends JpaRepository<BookCategory, Long> {

    List<BookCategory> findAllByBook(Book book);

    boolean existsByBookAndCategory(Book book, Category category);

    Optional<BookCategory> findByBookAndCategory(Book book, Category category);

    void deleteAllByBook(Book book);

    void deleteByBookAndCategory(Book book, Category category);

}
