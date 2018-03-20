import { Component, OnInit } from '@angular/core';
import { ExpenseService } from '../expense.service';
import { Expense } from '../expense';
import { Category } from '../category';
import { CategoryService } from '../category.service';

@Component({
  selector: 'app-expenses',
  templateUrl: './expenses.component.html',
  styleUrls: ['./expenses.component.css']
})
export class ExpensesComponent implements OnInit {

  expenses: Expense[];
  expenseCategories: Category[];
  public showForm = false;
  public newExpense = new Expense();

  constructor(private expenseService: ExpenseService, private categoryService: CategoryService) {}

  ngOnInit() {
    this.getExpenses();
    this.getExpenseCategories();
  }

  private getExpenses(): void {
    this.expenseService.getExpenses().subscribe(expenses => this.expenses = expenses);
  }

  private getExpenseCategories(): void {
    this.categoryService.getExpenseCategories().subscribe(categories => this.expenseCategories = categories);
  }

  submitForm(): void {
    const createExpenseCommand = {
      date: this.newExpense.date,
      value: this.newExpense.value,
      note: this.newExpense.note,
      categoryId: this.newExpense.category.id,
      type: 'NORMAL'
    };
    this.expenseService.createExpense(createExpenseCommand).subscribe((response) => {
      this.getExpenses();
    });
  }
}
