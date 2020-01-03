package de.duckdeer.expenses.repository;

import de.duckdeer.expenses.model.ExpenseFixed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseFixedRepository extends JpaRepository<ExpenseFixed, Long> {


}
