package de.duckdeer.expenses.controller;

import de.duckdeer.expenses.model.Category;
import de.duckdeer.expenses.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @RequestMapping(value="/categories", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Category> getAll() {
        Iterable<Category> catIt = categoryRepository.findAll();
        final List<Category> result = new ArrayList<>();
        for (Category category : catIt) {
            result.add(category);
        }
        return result;
    }

    @RequestMapping(value="/categories/income", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Category> getIncomeCategories() {
        return getCategoriesByType(Category.CategoryType.INCOME);
    }

    @RequestMapping(value="/categories/expense", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Category> getExpenseCategories() {
        return getCategoriesByType(Category.CategoryType.EXPENSE);
    }

    private List<Category> getCategoriesByType(Category.CategoryType type) {
        Iterable<Category> catIt = categoryRepository.findByType(type);
        List<Category> result = new ArrayList<>();
        for (Category category : catIt) {
            result.add(category);
        }
        return result;
    }

    @RequestMapping(value = "/categories/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Category getById(@PathVariable Long id) {
        Optional<Category> cat = categoryRepository.findById(id);
        if (cat.isPresent()) {
            return cat.get();
        } else {
            return null;
        }
    }

    @RequestMapping(value = "/categories/update", method = RequestMethod.POST)
    public ResponseEntity<String> updateCategory(@RequestBody Category category) {
        if (category != null) {
            categoryRepository.save(category);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @RequestMapping(value = "/categories/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> delete(@PathVariable Long id) {
        if (id != null) {
            Optional<Category> srvCatMaybe = categoryRepository.findById(id);
            if (srvCatMaybe.isPresent()) {
                Category srvCat = srvCatMaybe.get();
                srvCat.setActive(Boolean.FALSE);
                categoryRepository.save(srvCat);
            }

            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
