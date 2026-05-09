import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon'

@Component({
  selector: 'app-stock-alerts',
  standalone: true,
  imports: [CommonModule, MatIconModule],
  templateUrl: './stock-alerts.component.html',
  styleUrls: ['./stock-alerts.component.css']
})
export class StockAlertsComponent {

}
