package de.duckdeer.expenses.repository;

import de.duckdeer.expenses.model.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    List<Category> findByType(Category.CategoryType type);
}
