package de.duckdeer.expenses;

import de.duckdeer.expenses.model.Category;
import de.duckdeer.expenses.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ExpensesApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExpensesApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner demo(CategoryRepository categoryRepository) {
//        return (args) -> {
//
//
//            //categoryRepository.save(Category.builder().name("Test").type(Category.CategoryType.INCOME).build());
//            categoryRepository.save(new Category("Test", Category.CategoryType.INCOME));
//
//
//            // fetch all
//            System.out.println("Find all categories");
//            System.out.println("----------------------");
//            for (Category category : categoryRepository.findAll()) {
//                System.out.println(category.getName());
//            }
//        };
//    }
}
