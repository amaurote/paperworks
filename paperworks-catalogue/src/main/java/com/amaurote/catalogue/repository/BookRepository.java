package com.amaurote.catalogue.repository;

import com.amaurote.catalogue.domain.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findOneByCatalogueId(Long catId);

}
