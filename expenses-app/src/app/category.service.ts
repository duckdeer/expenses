import { Injectable } from '@angular/core';
import { Http, Response, RequestOptions, Headers } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { of } from 'rxjs/observable/of';
import 'rxjs/add/operator/map';

import { CATEGORIES } from './mock-categories';
import { Category } from './category';

@Injectable()
export class CategoryService {
  private baseUrl: string = 'localhost:8080';
  constructor(private http: Http) { }

  ngOnInit() { }

  getCategories(): Observable<Category[]> {
    //let categories$ = this.http.get(`${this.baseUrl}/categories`, {headers: this.getHeaders()}).map(mapCategories);
    //return categories$;
    return of(CATEGORIES);
  }

  private getHeaders(): Headers {
    let headers = new Headers();
    headers.append('Accept', 'application/json');
    return headers;
  }

}
  function mapCategories(response: Response): Category[] {
    return response.json().results.map(toCategory);
  }

  function toCategory(r: any): Category {
    let category = <Category>({
      id: r.id,
      name: r.name,
      type: r.type
    });
    return category;
  }

