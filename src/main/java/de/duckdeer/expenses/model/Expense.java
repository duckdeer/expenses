package de.duckdeer.expenses.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "EXPENSE")
public class Expense {

    public enum ExpenseType {FIXED, NORMAL}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "DATE")
    private LocalDate date;

    @Column(name = "VALUE")
    private BigDecimal value;

    @Column(name = "NOTE")
    private String note;

    @ManyToOne(targetEntity = Category.class)
    private Category category;

    @Column(name = "TYPE")
    @Enumerated(value = EnumType.STRING)
    private ExpenseType type;
}
