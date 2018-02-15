package de.duckdeer.expenses.controller;

import de.duckdeer.expenses.model.Category;
import de.duckdeer.expenses.repository.ExpenseRepository;
import de.duckdeer.expenses.model.Expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ExpenseController {

    @Autowired
    private ExpenseRepository expenseRepository;

    @RequestMapping(value = "expenses/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    private List<Expense> listAllExpenses() {
        return expenseRepository.findAll(Sort.by("date").ascending());
    }

    @RequestMapping(value = "/expenses/{page}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    private Page<Expense> listExpensesForPage(@PathVariable("page") Pageable pageable) {
        return expenseRepository.findAll(pageable);
    }

    @RequestMapping(value ="/expenses/listByCategory", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    private List<Expense> listByCategory(@RequestBody Long categoryId) {
        return expenseRepository.listByCategory(categoryId);
    }

    @RequestMapping(value ="/expenses/listByCategories", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    private List<Expense> listByCategory(@RequestBody List<Long> categoryIds) {
        return expenseRepository.listByCategories(categoryIds);
    }

    @RequestMapping(value = "expenses/updatetExpense", method = RequestMethod.POST)
    private ResponseEntity<String> updateExpense(@RequestBody Expense expense) {
        if (expense != null) {
            expenseRepository.save(expense);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
    }

    @RequestMapping(value = "expense/deleteExpense", method = RequestMethod.POST)
    private ResponseEntity<String> deleteExpense(@RequestBody Expense expense) {
        if (expense != null) {
            expenseRepository.delete(expense);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
    }
}
