
export interface MovementHistory {
  id:           number;
  productId:    number;
  type:         MoventType;
  quantity:     number;
  reason:       string;
  timestamp:    Date;
  updatedStock: number;
}

export enum MoventType {
  IN = 'IN',
  OUT = 'OUT'
}

