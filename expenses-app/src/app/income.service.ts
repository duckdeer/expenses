import { Injectable } from '@angular/core';
import { Http, Response, RequestOptions, Headers } from '@angular/http';
import { Observable } from 'rxjs/Observable';

import { BaseServiceService } from './base-service.service';
import { Income } from "./income";
import { CategoryService } from './category.service';

@Injectable()
export class IncomeService extends BaseServiceService {

  constructor(private http: Http, private categoryService: CategoryService) {
    super();
  }

  getIncomes(): Observable<Income[]> {
    return this.http.get(`${this.baseUrl}/incomes`, {headers: this.getHeaders()}).map((r) => r.json());
  }

  createIncome(income: CreateIncomeCommand): Observable<Income> {
    return this.http.post(`${this.baseUrl}/incomes/update`, income, {headers: this.getHeaders()}).map(r => r.json());
  }

}

export interface CreateIncomeCommand {

  name: String;
  value: Number;
  validFrom: String;
  validThru: String;
  type: String;
  categoryId: Number;

}
