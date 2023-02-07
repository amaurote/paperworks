package com.amaurote.catalog.repository;

import com.amaurote.domain.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findOneByCatalogId(Long catId);

    List<Book> findByTitleContainsIgnoreCase(String term);

}
