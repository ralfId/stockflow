import { Component, effect, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon'
import { InventoryStoreService } from 'src/app/services/inventorystore.service';
import { Product } from 'src/app/core/models';
import { LoaderComponent, ErrorviewComponent, SkeletonComponent } from 'src/app/shared/components/loaders';

@Component({
  selector: 'app-stock-alerts',
  standalone: true,
  imports: [CommonModule, MatIconModule,
    LoaderComponent, ErrorviewComponent, SkeletonComponent
  ],
  templateUrl: './stock-alerts.component.html',
  styleUrls: ['./stock-alerts.component.css']
})
export class StockAlertsComponent implements OnInit {

  private inventoryStoreService = inject(InventoryStoreService);

  listaALertas: Product[] = [];
  productAlert = this.inventoryStoreService.productAlertSignal;

  constructor() {
    effect(() => {
      const content = this.productAlert()?.content;
      if (content) this.listaALertas = content;
    });
  }

  ngOnInit(): void {
    this.inventoryStoreService.loadProductsAlerts();
  }

  onSelectedAlert(productAlert: Product){
      this.inventoryStoreService.loadProductByID(productAlert.id);
  }
}
