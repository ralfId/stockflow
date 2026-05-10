import { Component, effect, inject } from '@angular/core';
import { RouterLink } from '@angular/router';
import { InventoryStoreService } from 'src/app/services/inventorystore.service';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './header.component.html'
})
export class HeaderComponent {

  private inventoryStoreService = inject(InventoryStoreService);

  totalAlerts: number = 0;

  constructor() {
    effect(() => {
      this.totalAlerts = this.inventoryStoreService.totalAlertsCount();
    });
  }

}
