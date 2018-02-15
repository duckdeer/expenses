import { Injectable } from '@angular/core';
import { Http, Response, RequestOptions, Headers } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { of } from 'rxjs/observable/of';
import 'rxjs/add/operator/map';

import { CATEGORIES } from './mock-categories';
import { Category } from './category';

@Injectable()
export class CategoryService {
  private baseUrl: string = '/api';
  constructor(private http: Http) { }

  ngOnInit() { }

  getCategories(): Observable<Category[]> {
    let categories = this.http.get(`${this.baseUrl}/categories`, {headers: this.getHeaders()}).map((r) => this.mapCategories(r));
    return categories;
    //return of(CATEGORIES);
  }

  postCategory(category: Category): Observable<Category> {
    return this.http.post(`${this.baseUrl}/categories/update`, category).map((r) => this.toCategory(r));
  }

  private getHeaders(): Headers {
    let headers = new Headers();
    headers.append('Accept', 'application/json');
    return headers;
  }

  private mapCategories(response: Response): Category[] {
    return response.json().map(this.toCategory);
  }

  private mapCategory(response: Response): Category {
    return this.toCategory(response.json());
  }

  private toCategory(r: any): Category {
    let category = <Category>({
      id: r.id,
      name: r.name,
      type: r.type
    });
    return category;
  }

}

