package de.duckdeer.expenses.controller;

import de.duckdeer.expenses.model.Category;
import de.duckdeer.expenses.model.Income;
import de.duckdeer.expenses.repository.CategoryRepository;
import de.duckdeer.expenses.repository.IncomeRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api")
public class IncomeController {

    @Autowired
    private IncomeRepository incomeRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @RequestMapping(value = "incomes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Income> getAll() {
        Iterable<Income> incomeIt = incomeRepository.findAll();
        List<Income> result = new ArrayList<>();
        for (Income income : incomeIt) {
            result.add(income);
        }
        return result;
    }

    @RequestMapping(value = "incomes/update", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Income> update(@RequestBody CreateIncomeCommand createIncomeCommand) {
        if (createIncomeCommand != null) {
            final Category category = categoryRepository.findById(createIncomeCommand.getCategoryId()).get();
            Income income = createIncome(createIncomeCommand, category);
            incomeRepository.save(income);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
    }

    @RequestMapping(value = "/incomes/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> delete(@PathVariable Long id) {
        if (id != null) {
            Optional<Income> srvIncomeMaybe = incomeRepository.findById(id);
            if (srvIncomeMaybe.isPresent()) {
                Income srvIncome = srvIncomeMaybe.get();
                incomeRepository.delete(srvIncome);
            }

            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    private Income createIncome(@RequestBody CreateIncomeCommand createIncomeCommand, Category category) {
        Income income = new Income();
        income.setCategory(category);
        income.setName(createIncomeCommand.getName());
        income.setValue(createIncomeCommand.getValue());
        income.setValidFrom(createIncomeCommand.getValidFrom());
        income.setValidThru(createIncomeCommand.getValidThru());
        income.setType(createIncomeCommand.getType());
        return income;
    }

    @Data
    public static class CreateIncomeCommand {

        private String name;
        private BigDecimal value;
        private LocalDate validFrom;
        private LocalDate validThru;
        private Income.IncomeType type;
        private Long categoryId;
    }
}
