import { Component, OnInit } from '@angular/core';
import { Income } from '../income';
import { IncomeService } from '../income.service';
import { Category } from '../category';
import { CategoryService } from '../category.service';

@Component({
  selector: 'app-incomes',
  templateUrl: './incomes.component.html',
  styleUrls: ['./incomes.component.css']
})
export class IncomesComponent implements OnInit {

  incomes: Income[];
  categories: Category[];

  public showForm = false;
  public newIncome = new Income();

  constructor(private incomeService: IncomeService, private categoryService: CategoryService) { }

  ngOnInit() {
    this.getIncomes();
  }

  private getIncomes(): void {
    this.incomeService.getIncomes().subscribe(incomes => this.incomes = incomes);
  }

  private getCategories(): void {
    this.categoryService.getCategories().subscribe(categories => this.categories = categories);
  }

  submitForm(): void {
    this.incomeService.postIncome(this.newIncome).subscribe((response) => {
      this.getIncomes();
    });
  }
}
