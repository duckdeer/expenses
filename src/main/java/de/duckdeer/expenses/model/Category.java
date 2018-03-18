package de.duckdeer.expenses.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "CATEGORY")
public class Category {

    public Category() {
        super();
    }

    public Category(String name, CategoryType type) {
        super();
        this.name = name;
        this.type = type;
    }

    public enum CategoryType {INCOME, EXPENSE}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "TYPE")
    @Enumerated(value = EnumType.STRING)
    private CategoryType type;

    @Column(name = "ACTIVE")
    private Boolean active = Boolean.TRUE;
}
