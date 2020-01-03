package de.duckdeer.expenses.command.expenses;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CreateExpenseCommand {
    private LocalDate date;
    private BigDecimal value;
    private String note;
    private Long categoryId;
}
