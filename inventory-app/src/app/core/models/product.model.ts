
export interface Product {
  id: number;
  sku: string;
  name: string;
  category: string;
  currentStock: number;
  minStock: number;
  unitPrice: number;
  stockAlert?: ProductAlert;
}

export enum ProductAlert {
  OK = 'OK',
  BAJO = 'BAJO',
  CRITICO = 'CRITICO'
}
