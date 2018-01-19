package de.duckdeer.expenses.repository;

import de.duckdeer.expenses.model.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
