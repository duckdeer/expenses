import { Injectable, Inject } from '@angular/core';
import { Http, Response, RequestOptions, Headers } from '@angular/http';

@Injectable()
export class BaseServiceService {

  protected baseUrl: string = '/api';
  constructor() {
  }

  protected getHeaders(): Headers {
    let headers = new Headers();
    headers.append('Accept', 'application/json');
    return headers;
  }
}
