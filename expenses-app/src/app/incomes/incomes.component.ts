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
  incomeCategories: Category[];
  currentDate: Date = new Date();

  public showForm = false;
  public newIncome = new Income();

  constructor(private incomeService: IncomeService, private categoryService: CategoryService) { }

  ngOnInit() {
    this.getIncomes();
    this.getIncomeCategories();
  }

  private getIncomes(): void {
    this.incomeService.getIncomes().subscribe(incomes => this.incomes = incomes);
  }

  private getIncomeCategories(): void {
    this.categoryService.getIncomeCategories().subscribe(categories => this.incomeCategories = categories);
  }

  submitForm(): void {
    const createIncomeCommand = {
      categoryId: this.newIncome.category.id,
      name: this.newIncome.name,
      type: this.newIncome.type,
      validFrom: this.newIncome.validFrom,
      validThru: this.newIncome.validThru,
      value: this.newIncome.value
    };
    this.incomeService.createIncome(createIncomeCommand).subscribe((response) => {
      this.getIncomes();
    });
  }
}
