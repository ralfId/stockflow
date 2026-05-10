import { DatePipe } from '@angular/common';
import { Component, computed, effect, inject, OnInit } from '@angular/core';
import { MovementHistory } from 'src/app/core/models/movement-history.model';
import { InventoryStoreService } from 'src/app/services/inventorystore.service';
import { LoaderComponent, ErrorviewComponent, SkeletonComponent } from 'src/app/shared/components/loaders';

@Component({
  selector: 'app-history-movement',
  standalone: true,
  imports: [DatePipe, LoaderComponent, ErrorviewComponent, SkeletonComponent],
  templateUrl: './history-movement.component.html',
})
export class HistoryMovementComponent implements OnInit {

  private inventoryStoreService = inject(InventoryStoreService);
  selectedProduct = this.inventoryStoreService.selectedProductSignal;
  history = this.inventoryStoreService.productMovementHistorySignal;

  productName: String = '';
  productCategory: String = '';

  isHistoryLoaded = computed(() => !!this.history());

  constructor() {
    effect(() => {
      const product = this.selectedProduct();

      if (product?.id) {
        this.inventoryStoreService.loadProductMovementHistory(product.id);
        this.productName = product.name;
        this.productCategory = product.category;
      }
    });

  }

  ngOnInit(): void {
    const currentProduct = this.selectedProduct();
    if (currentProduct?.id) {
      this.inventoryStoreService.loadProductMovementHistory(currentProduct.id);
    }
  }
}


