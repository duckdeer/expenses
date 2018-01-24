package de.duckdeer.expenses.repository;

import de.duckdeer.expenses.model.Category;
import de.duckdeer.expenses.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> listByCategory(@Param("catId") Long categoryId);

    List<Expense> listByCategories(@Param("catIds") List<Long> categoryIds);
}
