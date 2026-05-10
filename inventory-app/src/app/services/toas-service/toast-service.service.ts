import { Injectable, signal } from '@angular/core';

export interface Toast {
  id: number;
  title: string;
  message: string;
  type: 'success' | 'error' | 'info';
}

@Injectable({ providedIn: 'root' })
export class ToastService {

  constructor() { }

  // signal privado para la lista de toasts
  private toastsSignal = signal<Toast[]>([]);

  // signal de solo lectura
  toasts = this.toastsSignal.asReadonly();

  show(title: string, message: string, type: 'success' | 'error' | 'info' = 'info') {
    const id = Date.now();
    const newToast: Toast = { id, title, message, type };

    // agregamos el nuevo toast al array
    this.toastsSignal.update(all => [...all, newToast]);

    // auto-eliminar despues de 5 segundos
    setTimeout(() => { this.remove(id); }, 5000);
  }

  showSuccess(title: string, msg: string) { this.show(title, msg, 'success'); }
  showError(title: string, msg: string) { this.show(title, msg, 'error'); }
  showInfo(title: string, msg: string) { this.show(title, msg, 'info'); }

  // eliminar el toast manualmente
  remove(id: number) {
    this.toastsSignal.update(all => all.filter(t => t.id !== id));
  }
}
