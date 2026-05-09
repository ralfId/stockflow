import { AfterViewInit, Component, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatPaginator, MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';

@Component({
  selector: 'app-product',
  standalone: true,
  imports: [CommonModule, MatTableModule, MatPaginatorModule, MatSortModule],
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent implements AfterViewInit {

  columnas: string[] = ['sku', 'name', 'category', 'currentStock', 'minStock', 'unitPrice', 'stockAlert'];
  datos = new MatTableDataSource<any>([
    { id: 1, sku: 'sku-1', name: 'producto 1', category: 'category-1', currentStock: 10, minStock: 5, unitPrice: 12.5, stockAlert: 'OK' },
    { id: 2, sku: 'sku-2', name: 'producto 2', category: 'category-2', currentStock: 10, minStock: 5, unitPrice: 12.5, stockAlert: 'BAJO' },
    { id: 3, sku: 'sku-3', name: 'producto 3', category: 'category-3', currentStock: 10, minStock: 5, unitPrice: 12.5, stockAlert: 'CRITICO' },

  ]);

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  ngAfterViewInit() {
    // Vinculamos el paginador al datasource
    this.datos.paginator = this.paginator;
  }

  onTablePageChange(event: PageEvent) {
    console.log('Nueva página:', event.pageIndex);
    console.log('Tamaño de página:', event.pageSize);

    // llamar api
  }
}
