import { Component, OnInit } from '@angular/core';

import { Category } from '../category';
import { CategoryService } from '../category.service';

@Component({
  selector: 'app-categories',
  templateUrl: './categories.component.html',
  styleUrls: ['./categories.component.css']
})
export class CategoriesComponent implements OnInit {

  categories: Category[];

  public showForm = false;
  public newCategory = new Category();

  constructor(private categoryService: CategoryService) { }

  ngOnInit() {
    this.getCategories();
    console.log("Loaded categories");
  }

  getCategories(): void {
    this.categoryService.getCategories().subscribe(categories => this.categories = categories);
  }

  submitForm(): void {
    this.categoryService.postCategory(this.newCategory).subscribe((response) => {
      this.getCategories();
    });
  }

  remove(category: Category): void {
    this.categoryService.deleteCategory(category).subscribe((response) => {
      this.getCategories();
    });
    console.log("Delete " + category.name);
  }
}
