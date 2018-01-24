package de.duckdeer.expenses.repository;

import de.duckdeer.expenses.model.Category;
import de.duckdeer.expenses.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    /**
     * Find {@link Expense}s for certain {@link Category}
     *
     * @param categoryId
     *  id of specific {@link Category}
     * @return
     *  {@link Expense}s with {@link Category} identified by given id
     */
    List<Expense> listByCategory(@Param("catId") Long categoryId);

    /**
     * Find {@link Expense}s for certain {@link Category}s
     *
     * @param categoryIds
     *  ids of specific {@link Category}s
     * @return
     *  {@link Expense}s with {@link Category} identified by given id list
     */
    List<Expense> listByCategories(@Param("catIds") List<Long> categoryIds);
}
