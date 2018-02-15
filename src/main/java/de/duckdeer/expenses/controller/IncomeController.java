package de.duckdeer.expenses.controller;

import de.duckdeer.expenses.model.Income;
import de.duckdeer.expenses.repository.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IncomeController {

    @Autowired
    private IncomeRepository incomeRepository;

    @RequestMapping(value = "incomes/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
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
    public ResponseEntity<Income> update(@RequestBody Income income) {
        if (income != null) {
            incomeRepository.save(income);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
    }

    @RequestMapping(value = "incomes/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Income> delete(@RequestBody Income income) {
        if (income != null) {
            incomeRepository.delete(income);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
    }
}
