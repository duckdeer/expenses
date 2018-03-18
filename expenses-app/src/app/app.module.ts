import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpModule } from '@angular/http';
import { FormsModule } from '@angular/forms';


import { AppComponent } from './app.component';
import { CategoriesComponent } from './categories/categories.component';
import { CategoryService } from './category.service';
import { AppRoutingModule } from './/app-routing.module';
import { IncomesComponent } from './incomes/incomes.component';
import { ExpensesComponent } from './expenses/expenses.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { IncomeService } from './income.service';
import { ExpenseService } from './expense.service';
import { BaseServiceService } from './base-service.service';


@NgModule({
  declarations: [
    AppComponent,
    CategoriesComponent,
    IncomesComponent,
    ExpensesComponent,
    DashboardComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    AppRoutingModule
  ],
  providers: [CategoryService, IncomeService, ExpenseService, BaseServiceService],
  bootstrap: [AppComponent]
})
export class AppModule { }
