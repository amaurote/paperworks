package com.amaurote.catalogue.repository;

import com.amaurote.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByParentAndName(Category parent, String name);

    List<Category> findCategoriesByParent(Category parent);

}
