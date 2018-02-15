import { Category } from "./category";

export class Income {
  id: number;
  value: number;
  name: string;
  validFrom: Date;
  validThru: Date;
  type: string;
  category: Category;
}