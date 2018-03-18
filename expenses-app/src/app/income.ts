import { Category } from "./category";

export class Income {
  id: number;
  value: number;
  name: string;
  validFrom: String;
  validThru: String;
  type: string;
  category: Category;
}
