package de.duckdeer.expenses.command.expenses.fixed;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CreateExpenseFixedCommand {
    private LocalDate validFrom;
    private LocalDate validThru;
    private BigDecimal value;
    private String note;
    private Long categoryId;
}
