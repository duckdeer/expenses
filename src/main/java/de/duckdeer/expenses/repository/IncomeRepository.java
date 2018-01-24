package de.duckdeer.expenses.repository;

import de.duckdeer.expenses.model.Income;
import org.springframework.data.repository.CrudRepository;

public interface IncomeRepository extends CrudRepository<Income, Long> {
}
