package de.duckdeer.expenses.controller;

import de.duckdeer.expenses.command.expenses.fixed.CreateExpenseFixedCommand;
import de.duckdeer.expenses.command.expenses.fixed.UpdateExpenseFixedCommand;
import de.duckdeer.expenses.model.ExpenseFixed;
import de.duckdeer.expenses.repository.CategoryRepository;
import de.duckdeer.expenses.repository.ExpenseFixedRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/expenses/fixed")
public class ExpenseFixedController {

    private final Logger log = LoggerFactory.getLogger(ExpenseFixedController.class);

    private ExpenseFixedRepository expenseFixedRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public ExpenseFixedController(ExpenseFixedRepository expenseFixedRepository, CategoryRepository categoryRepository) {
        this.expenseFixedRepository = expenseFixedRepository;
        this.categoryRepository = categoryRepository;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<ExpenseFixed> insert(@RequestBody CreateExpenseFixedCommand command) {
        if (command != null) {
            ExpenseFixed expenseFixed = new ExpenseFixed();
            expenseFixed.setValidFrom(command.getValidFrom());
            expenseFixed.setValidThru(command.getValidThru());
            expenseFixed.setNote(command.getNote());
            expenseFixed.setCategory(categoryRepository.findById(command.getCategoryId()).get());
            expenseFixed.setValue(command.getValue());

            ExpenseFixed persistedExpense = expenseFixedRepository.save(expenseFixed);
            log.info(String.format("Persisted new fixed expense: %s", persistedExpense.toString()));
            return ResponseEntity.ok(persistedExpense);
        }

        return ResponseEntity.badRequest().build();
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ExpenseFixed> update(@RequestBody UpdateExpenseFixedCommand command) {
        if (command != null) {
            Optional<ExpenseFixed> maybe = expenseFixedRepository.findById(command.getId());
            if (maybe.isPresent()) {
                ExpenseFixed persistedExpense = maybe.get();
                persistedExpense.setValidFrom(command.getValidFrom());
                persistedExpense.setValidThru(command.getValidThru());
                persistedExpense.setNote(command.getNote());
                persistedExpense.setCategory(categoryRepository.findById(command.getCategoryId()).get());
                persistedExpense.setValue(command.getValue());

                ExpenseFixed updatedExpense = expenseFixedRepository.save(persistedExpense);
                log.info(String.format("Updated fixed expense: %s", persistedExpense.toString()));
                return ResponseEntity.ok(updatedExpense);
            }
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        if (!expenseFixedRepository.existsById(id)) {
            log.error(String.format("Fixed expense with id=%d not found. Deletion not possible.", id));
            return ResponseEntity.badRequest().build();
        }

        expenseFixedRepository.deleteById(id);
        log.info(String.format("Deleted fixed expense with id=%d", id));
        return ResponseEntity.ok().build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<ExpenseFixed> getAll() {
        List<ExpenseFixed> result = expenseFixedRepository.findAll(Sort.by("note").ascending());
        log.info(String.format("Return all fixed expenses. Found %d.", result.size()));
        return result;
    }
}
