import { Component, effect, inject, OnInit } from '@angular/core';
import { CommonModule, CurrencyPipe } from '@angular/common';
import { InventoryStoreService } from 'src/app/services/inventorystore.service';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, CurrencyPipe, RouterLink],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  private inventoryStoreService = inject(InventoryStoreService);

  totalProducts: number = 0;
  totalInventoryValue: number = 0;
  totalAlerts: number = 0;
  totalAlertsLow: number = 0;
  totalAlertsCritical: number = 0;

  constructor() {

    effect(() => {
      this.totalProducts = this.inventoryStoreService.totalProducts();
      this.totalInventoryValue = this.inventoryStoreService.totalInventoryValue();
      this.totalAlerts = this.inventoryStoreService.totalAlertsCount();
      this.totalAlertsLow = this.inventoryStoreService.totalLowAlertCount()
      this.totalAlertsCritical = this.inventoryStoreService.totalCriticalAlertsCount();
    });

  }

  ngOnInit(): void {
    this.inventoryStoreService.loadPageableProducts({ category: '', page: 0, size: 5 });
    this.inventoryStoreService.loadProductsAlerts();
  }
}
