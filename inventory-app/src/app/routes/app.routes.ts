import { Routes } from '@angular/router';

export const appRoutes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'dashboard' },
  {
    path: 'dashboard',
    loadComponent: () =>
      import('../features/dashboard/dashboard.component').then((m) => m.DashboardComponent),
  },
  {
    path: 'productos',
    loadComponent: () =>
      import('../features/product/product.component').then((m) => m.ProductComponent),
  },
  {
    path: 'registrar-movimientos',
    loadComponent: () =>
      import('../features/movement/register-movement/movement.component').then((m) => m.MovementComponent),
  },
  {
    path: 'historial-movimientos',
    loadComponent: () =>
      import('../features/movement/history-movement/history-movement.component').then((m) => m.HistoryMovementComponent),
  },
  {
    path: 'stock-alerts',
    loadComponent: () =>
      import('../features/stock-alerts/stock-alerts.component').then((m) => m.StockAlertsComponent),
  },
  { path: '**', redirectTo: 'dashboard' },
];
