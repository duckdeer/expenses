import { Category } from "./category";

export class Expense {
  id: number;
  date: String;
  value: number;
  note: string;
  category: Category;
  type: string;
}
