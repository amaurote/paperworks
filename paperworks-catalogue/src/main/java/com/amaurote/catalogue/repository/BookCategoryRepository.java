package com.amaurote.catalogue.repository;

import com.amaurote.domain.entity.Book;
import com.amaurote.domain.entity.BookCategory;
import com.amaurote.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookCategoryRepository extends JpaRepository<BookCategory, Long> {

    List<BookCategory> findAllByBook(Book book);

    boolean existsByBookAndCategory(Book book, Category category);

}
