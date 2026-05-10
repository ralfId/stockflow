import { Product } from "./product.model";

export interface APIResponse<T> {
  success:    boolean;
  statusCode: number;
  data:       T;
}

export interface Pageable<K> {
  content:       K[];
  page:          number;
  size:          number;
  totalElements: number;
  totalPages:    number;
  message:       null;
}


