package de.duckdeer.expenses.command.expenses.fixed;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class UpdateExpenseFixedCommand {
    private Long id;
    private LocalDate validFrom;
    private LocalDate validThru;
    private BigDecimal value;
    private String note;
    private Long categoryId;
}
