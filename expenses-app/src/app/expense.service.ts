import { Injectable } from '@angular/core';
import { Expense } from './expense';
import { BaseServiceService } from './base-service.service';
import { Http } from '@angular/http';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class ExpenseService extends BaseServiceService {

  constructor(private http: Http) {
    super();
  }

  getExpenses(): Observable<Expense[]> {
    return this.http.get(`${this.baseUrl}/expenses`, {headers: this.getHeaders()}).map((r) => r.json());
  }

  createExpense(expense: CreateExpenseCommand): Observable<Expense> {
    return this.http.post(`${this.baseUrl}/expenses/update`, expense, {headers: this.getHeaders()}).map(r => r.json());
  }
}

export interface CreateExpenseCommand {

  date: String;
  value: Number;
  note: String;
  categoryId: Number;
  type: String;
}
