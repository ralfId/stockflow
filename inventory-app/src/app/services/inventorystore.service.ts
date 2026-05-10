import { HttpClient, HttpParams } from '@angular/common/http';
import { computed, inject, Injectable, signal } from '@angular/core';
import { ProductRequetst } from '../core/interfaces/product-request.interface';
import { APIResponse, Pageable, Product, ProductAlert } from '../core/models';

// export interface Product {
//   id: number;
//   sku: string
//   name: string;
//   category: string;
//   currentStock: number;
//   minStock: number;
//   unitPrice: number;
// }

@Injectable({ providedIn: 'root' })
export class InventoryStoreService {

  private readonly baseUrl = "http://localhost:8080/api/v1";
  private httpClient = inject(HttpClient);
  private _productSignal = signal<Pageable<Product> | null>(null);
  private _productAlertSignal = signal<Pageable<Product> | null>(null);
  private _selectedProductSignal = signal<Product | null>(null);

  // signal de productos de solo lectura
  public productsignal = this._productSignal.asReadonly();
  public productAlertSignal = this._productAlertSignal.asReadonly();
  public selectedProductSignal = this._selectedProductSignal.asReadonly();

  // propiedades derivadas (computed)
  public totalProducts = computed(() => (this.productsignal()?.totalElements || 0));
  public totalInventoryValue = computed(() => {
    const inventoryValue = this.productsignal()?.message || '0';
    return +inventoryValue;
  })


  private alertStatus = computed(() => {
    const products = this.productAlertSignal()?.content || [];

    return products.reduce((acc, prod) => {
      if (prod.stockAlert === ProductAlert.BAJO) acc.low++;
      if (prod.stockAlert === ProductAlert.CRITICO) acc.critical++;
      return acc;
    }, { low: 0, critical: 0 });
  });

  public totalLowAlertCount = computed(() => this.alertStatus().low);
  public totalCriticalAlertsCount = computed(() => this.alertStatus().critical);
  public totalAlertsCount = computed(() => this.alertStatus().low + this.alertStatus().critical);

  constructor() { }

  loadPageableProducts(filters: ProductRequetst) {
    const params = this.buildHttpParams(filters);

    this.httpClient.get<any>(`${this.baseUrl}/products`, { params })
      .subscribe({
        next: (apiResp: APIResponse<Pageable<Product>>) => {
          // guardamos la respuesta en el Signal
          const content = apiResp.data.content.map((prod) => ({ ...prod, stockAlert: this.getAlertStatus(prod.currentStock, prod.minStock) }));
          this._productSignal.set({ ...apiResp.data, content });
        }
      });
  }

  private buildHttpParams(params: Record<string, any>): HttpParams {
    let httpParams = new HttpParams();
    Object.keys(params).forEach(key => {
      if (params[key] !== null && params[key] !== undefined && params[key] !== '') {
        httpParams = httpParams.set(key, params[key].toString());
      }
    });
    return httpParams;
  }

  private getAlertStatus(current: number, min: number): ProductAlert {
    // Prioridad: Lo más grave primero
    if (current < min) return ProductAlert.CRITICO;
    if (current === min) return ProductAlert.BAJO;
    return ProductAlert.OK;
  }

  loadProductsAlerts() {
    const params = this.buildHttpParams({ page: 0, size: 100 });

    this.httpClient.get<any>(`${this.baseUrl}/alerts`, { params })
      .subscribe({
        next: (apiResp: APIResponse<Pageable<Product>>) => {
          // guardamos la respuesta en el Signal de alertas
          const content = apiResp.data.content.map((prod) => ({ ...prod, stockAlert: this.getAlertStatus(prod.currentStock, prod.minStock) }));
          this._productAlertSignal.set({ ...apiResp.data, content });
        }
      });
  }

  loadProductByID(producID: number) {

    this.httpClient.get<any>(`${this.baseUrl}/products/${producID}`)
      .subscribe({
        next: (apiResp: APIResponse<Product>) => {
          this._selectedProductSignal.set(apiResp.data);
        }
      });
  }
}
