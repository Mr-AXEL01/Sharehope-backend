package net.axel.sharehope.repository;

import net.axel.sharehope.domain.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
  }