package de.duckdeer.expenses.controller;

import de.duckdeer.expenses.model.Category;
import de.duckdeer.expenses.model.Expense;
import de.duckdeer.expenses.repository.CategoryRepository;
import de.duckdeer.expenses.repository.ExpenseRepository;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.File;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.Date;

@Controller
@RequestMapping("/api")
public class ExcelImportController {

    public static final String FILE_PATH = "C:\\temp\\expense_data.xlsx";

    private ExpenseRepository expenseRepository;
    private ExpenseController expenseController;
    private CategoryRepository categoryRepository;

    @Autowired
    public ExcelImportController(ExpenseRepository expenseRepository, ExpenseController expenseController, CategoryRepository categoryRepository) {
        this.expenseRepository = expenseRepository;
        this.expenseController = expenseController;
        this.categoryRepository = categoryRepository;
    }

    /**
     * Imports expense data for initial database filling. All existing entries will be truncated before importing.
     * <p>
     * File to import must be placed at <b>C:\temp\expense_data.xlsx</b>.
     * File must have a header row with the columns: Datum - Kategorie - Betrag - Kommentar
     * The "Kommentar" column can be empty, all other columns are mandatory.
     *
     * @return message with number of imported rows
     * @throws Exception
     */
    @RequestMapping(value = "/import", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String doImport() throws Exception {
        expenseController.cleanExpenses();

        Workbook workbook = WorkbookFactory.create(new File(FILE_PATH));
        Sheet sheet = workbook.getSheetAt(0);
        DataFormatter dataFormatter = new DataFormatter();

        Expense expense = null;
        int rowCount = 0;

        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue;
            }

            expense = new Expense();

            setDate(expense, row);
            setCategory(expense, row);
            setValue(expense, row);
            setNote(expense, row);

            expense.setType(Expense.ExpenseType.NORMAL);

            expenseRepository.save(expense);
            rowCount++;

            if (rowCount % 250 == 0) {
                System.out.println("Imported " + rowCount + " rows.");
            }
        }

        workbook.close();

        return "Imported " + rowCount + " rows.";
    }

    private void setNote(Expense expense, Row row) {
        String note = null;
        if (row.getCell(3) != null) {
            note = row.getCell(3).getStringCellValue();
        }
        expense.setNote(note);
    }

    private void setValue(Expense expense, Row row) {
        double value = row.getCell(2).getNumericCellValue();
        expense.setValue(BigDecimal.valueOf(value));
    }

    private void setCategory(Expense expense, Row row) {
        String categoryName = row.getCell(1).getStringCellValue();
        Category category = categoryRepository.findByName(categoryName);
        expense.setCategory(category);
    }

    private void setDate(Expense expense, Row row) {
        Date date = row.getCell(0).getDateCellValue();
        expense.setDate(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }
}
