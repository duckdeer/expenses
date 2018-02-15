import { Category } from "./category";

export class Expense {
  id: number;
  date: Date;
  value: number;
  note: string;
  category: Category;
  type: string;
}