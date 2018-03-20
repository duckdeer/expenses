package de.duckdeer.expenses.controller;

import de.duckdeer.expenses.model.Category;
import de.duckdeer.expenses.model.Income;
import de.duckdeer.expenses.repository.CategoryRepository;
import de.duckdeer.expenses.repository.ExpenseRepository;
import de.duckdeer.expenses.model.Expense;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/api")
public class ExpenseController {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

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

    private Expense createExpense(CreateExpenseCommand createExpenseCommand, Category category) {
        Expense expense = new Expense();
        expense.setDate(createExpenseCommand.getDate());
        expense.setValue(createExpenseCommand.getValue());
        expense.setNote(createExpenseCommand.getNote());
        expense.setCategory(category);
        expense.setType(createExpenseCommand.getType());
        return expense;
    }

    @RequestMapping(value = "expense/delete", method = RequestMethod.POST)
    public ResponseEntity<String> delete(@RequestBody Expense expense) {
        if (expense != null) {
            expenseRepository.delete(expense);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
    }

    @Data
    public static class CreateExpenseCommand {

        private LocalDate date;
        private BigDecimal value;
        private String note;
        private Long categoryId;
        private Expense.ExpenseType type;
    }
}
