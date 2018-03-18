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
    return this.http.get(`${this.baseUrl}/incomes`, {headers: this.getHeaders()}).map((r) => this.mapIncomes(r));
  }

  private mapIncomes(response: Response): Income[] {
    return response.json().map(this.toIncome);
  }

  private mapIncome(response: Response): Income {
    return this.toIncome(response.json());
  }

  postIncome(income: Income): Observable<Income> {
    return this.http.post(`${this.baseUrl}/incomes/update`, income).map((r) => this.toIncome(r));
  }

  private toIncome(r: any): Income {
    let income = <Income>({
      id: r.id,
      value: r.value,
      name: r.name,
      validFrom: r.validFrom,
      validThru: r.validThru,
      type: r.type,
      category: this.categoryService.toCategory(r.category)
    });
    return income;
  }
}
