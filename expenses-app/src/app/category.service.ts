import { Injectable } from '@angular/core';
import { Http, Response, RequestOptions, Headers } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';

import { BaseServiceService } from './base-service.service';
import { Category } from './category';

@Injectable()
export class CategoryService extends BaseServiceService {
  constructor(private http: Http) {
    super();
   }

  ngOnInit() { }

  getCategories(): Observable<Category[]> {
    let categories = this.http.get(`${this.baseUrl}/categories`, {headers: this.getHeaders()}).map((r) => this.mapCategories(r));
    return categories;
  }

  getIncomeCategories(): Observable<Category[]> {
    return this.http.get(`${this.baseUrl}/categories/income`, {headers: this.getHeaders()}).map((r) => this.mapCategories(r));
  }

  getExpenseCategories(): Observable<Category[]> {
    return this.http.get(`${this.baseUrl}/categories/expense`, {headers: this.getHeaders()}).map((r) => this.mapCategories(r));
  }

  postCategory(category: Category): Observable<Category> {
    return this.http.post(`${this.baseUrl}/categories/update`, category).map((r) => this.toCategory(r));
  }

  deleteCategory(category: Category): Observable<Category> {
    const id = category.id;
    return this.http.delete(`${this.baseUrl}/categories/delete/${id}`).map((r) => this.toCategory(r));
  }

  private mapCategories(response: Response): Category[] {
    return response.json().map(this.toCategory);
  }

  private mapCategory(response: Response): Category {
    return this.toCategory(response.json());
  }

  public toCategory(r: any): Category {
    let category = <Category>({
      id: r.id,
      name: r.name,
      type: r.type
    });
    return category;
  }
}

