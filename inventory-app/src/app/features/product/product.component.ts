import { AfterViewInit, Component, effect, inject, OnInit, signal, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatPaginator, MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import { InventoryStoreService } from 'src/app/services/inventorystore.service';
import { Product } from 'src/app/core/models';
import { LoaderComponent, ErrorviewComponent, SkeletonComponent } from 'src/app/shared/components/loaders';

@Component({
  selector: 'app-product',
  standalone: true,
  imports: [CommonModule, MatTableModule, MatPaginatorModule, MatSortModule, LoaderComponent, ErrorviewComponent, SkeletonComponent],
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent implements OnInit {

  private inventoryStoreService = inject(InventoryStoreService);

  columnas: string[] = ['sku', 'name', 'category', 'currentStock', 'minStock', 'unitPrice', 'stockAlert'];
  dataSource = new MatTableDataSource<Product>([]);
  productsPage = this.inventoryStoreService.productsignal;

  @ViewChild(MatPaginator) paginator!: MatPaginator;


  selectedCategory = signal<string>(localStorage.getItem('selectedCategory') || 'Todas');

  categorias = ['Todas', 'Electrónicos', 'Alimentos', 'Ropa', 'Hogar'];

  constructor() {

    effect(() => {
      const category = this.selectedCategory();

      // guardar en LocalStorage
      localStorage.setItem('selectedCategory', category);

      this.cargarProductos(this.selectedCategory(), 0, 5);
    });

    // sincronizar el contenido con el DataSource de la tabla
    effect(() => {
      const content = this.productsPage()?.content || [];
      this.dataSource.data = content;
    });
  }

  ngOnInit(): void {
    this.inventoryStoreService.loadPageableProducts({ category: '', page: 0, size: 5 });
  }

  onTablePageChange(event: PageEvent) {
    this.cargarProductos(this.selectedCategory(), event.pageIndex, event.pageSize);
  }

  onCategoryChange(event: Event) {
    const value = (event.target as HTMLSelectElement).value;
    this.selectedCategory.set(value);
  }

  cargarProductos(category: string, page: number, size: number) {
    const categoryParam = category === 'Todas' ? '' : category;

    this.inventoryStoreService.loadPageableProducts({
      category: categoryParam,
      page: page,
      size: size
    });
  }

}
