import { Injectable } from '@angular/core';
import { Expense } from './expense';
import { BaseServiceService } from './base-service.service';
import { Http } from '@angular/http';

@Injectable()
export class ExpenseService extends BaseServiceService {

  constructor() {
    super();
  }

}
