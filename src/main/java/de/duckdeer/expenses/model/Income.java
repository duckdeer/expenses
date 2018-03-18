package de.duckdeer.expenses.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "INCOME")
public class Income {

    public enum IncomeType {MONTHLY, YEARLY, EXTRA}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "VALUE")
    private BigDecimal value;

    @Column(name = "NAME")
    private String name;

    @Column(name = "NOTE")
    private String note;

    @Column(name = "VALID_FROM")
    private LocalDate validFrom;

    @Column(name = "VALID_THRU")
    private LocalDate validThru;

    @Column(name = "TYPE")
    @Enumerated(value = EnumType.STRING)
    private IncomeType type = IncomeType.MONTHLY;

    @ManyToOne(targetEntity = Category.class)
    private Category category;
}
