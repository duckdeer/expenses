package de.duckdeer.expenses.controller;

import de.duckdeer.expenses.model.Category;
import de.duckdeer.expenses.repository.CategoryRepository;
import org.apache.logging.log4j.message.MapMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
}
