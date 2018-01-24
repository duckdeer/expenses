package de.duckdeer.expenses.controller;

import de.duckdeer.expenses.model.Category;
import de.duckdeer.expenses.repository.CategoryRepository;
import org.apache.logging.log4j.message.MapMessage;
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
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @RequestMapping(value="/categories", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Category> listCategories() {
        Iterable<Category> catIt = categoryRepository.findAll();
        final List<Category> result = new ArrayList<>();
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

    @RequestMapping(value = "/categories/updateCategory", method = RequestMethod.POST)
    public ResponseEntity<String> updateCategory(@RequestBody Category category) {
        if (category != null) {
            categoryRepository.save(category);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
    }

    @RequestMapping(value = "/categories/deleteCategory", method = RequestMethod.POST)
    public ResponseEntity<String> deleteCategory(@RequestBody Category category) {
        if (category != null) {
            categoryRepository.delete(category);
            // TODO correct return value
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
    }
}
