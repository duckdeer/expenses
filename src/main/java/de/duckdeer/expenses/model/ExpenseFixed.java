package de.duckdeer.expenses.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "EXPENSE_FIXED")
public class ExpenseFixed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "VALID_FROM")
    private LocalDate validFrom;

    @Column(name = "VALID_THRU")
    private LocalDate validThru;

    @Column(name = "VALUE")
    private BigDecimal value;

    @Column(name = "NOTE")
    private String note;

    @ManyToOne(targetEntity = Category.class)
    private Category category;
}
