package de.duckdeer.expenses.controller;

import de.duckdeer.expenses.command.expenses.CreateExpenseCommand;
import de.duckdeer.expenses.model.Category;
import de.duckdeer.expenses.model.Expense;
import de.duckdeer.expenses.repository.CategoryRepository;
import de.duckdeer.expenses.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/api")
public class ExpenseController {

    private ExpenseRepository expenseRepository;
    private CategoryRepository categoryRepository;
    private EntityManager entityManager;

    @Autowired
    public ExpenseController(ExpenseRepository expenseRepository, CategoryRepository categoryRepository, EntityManager entityManager) {
        this.expenseRepository = expenseRepository;
        this.categoryRepository = categoryRepository;
        this.entityManager = entityManager;
    }

    @RequestMapping(value = "expenses", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Expense> getAll() {
        return expenseRepository.findAll(Sort.by("date").ascending());
    }

    @RequestMapping(value = "/expenses/{page}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Page<Expense> listForPage(@PathVariable("page") Pageable pageable) {
        return expenseRepository.findAll(pageable);
    }

    @RequestMapping(value ="/expenses/listByCategory", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Expense> listByCategory(@RequestBody Long categoryId) {
        return expenseRepository.listByCategory(categoryId);
    }

    @RequestMapping(value ="/expenses/listByCategories", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Expense> listByCategory(@RequestBody List<Long> categoryIds) {
        return expenseRepository.listByCategories(categoryIds);
    }

    @RequestMapping(value = "expenses/update", method = RequestMethod.POST)
    public ResponseEntity<String> update(@RequestBody CreateExpenseCommand createExpenseCommand) {
        if (createExpenseCommand != null) {
            Category category = categoryRepository.findById(createExpenseCommand.getCategoryId()).get();
            Expense expense = createExpense(createExpenseCommand, category);
            expenseRepository.save(expense);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
    }

    /**
     * Deletes all entries from 'expense' table.
     */
    @Transactional
    public void cleanExpenses() {
        entityManager.createNativeQuery("TRUNCATE expense").executeUpdate();
        entityManager.flush();
    }

    private Expense createExpense(CreateExpenseCommand createExpenseCommand, Category category) {
        Expense expense = new Expense();
        expense.setDate(createExpenseCommand.getDate());
        expense.setValue(createExpenseCommand.getValue());
        expense.setNote(createExpenseCommand.getNote());
        expense.setCategory(category);
        return expense;
    }

    @RequestMapping(value = "expenses/delete", method = RequestMethod.POST)
    public ResponseEntity<String> delete(@RequestBody Expense expense) {
        if (expense != null) {
            expenseRepository.delete(expense);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
    }

    @RequestMapping(value = "expenses/range", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Expense> getExpensesForDateRange(@RequestParam("start") String start, @RequestParam("end") String end) {
        List<Expense> result = expenseRepository.findByDateBetween(LocalDate.parse(start), LocalDate.parse(end));
        return result.isEmpty() ? Collections.emptyList() : result;
    }
}
