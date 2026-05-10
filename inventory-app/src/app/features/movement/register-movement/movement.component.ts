import { Component, computed, effect, inject, OnDestroy, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { InventoryStoreService } from 'src/app/services/inventorystore.service';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { MovementRequest } from 'src/app/core/interfaces/movement-request.interface';

@Component({
  selector: 'app-movement',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './movement.component.html',
  styleUrls: ['./movement.component.css']
})
export class MovementComponent implements OnInit, OnDestroy {

  private inventoryStoreService = inject(InventoryStoreService);
  private fb = inject(FormBuilder);
  private valueChangesSub?: Subscription;
  private subscription = new Subscription();

  isSubmitting = signal(false);
  movementForm!: FormGroup;

  // Lista de productos con alerta desde el signal
  products = computed(() => this.inventoryStoreService.productAlertSignal()?.content ?? []);

  private updatingFields = false;

  ngOnInit(): void {
    this.inventoryStoreService.loadProductsAlerts();

    this.movementForm = this.fb.group({
      productId: [null, Validators.required],
      category: [{ value: '', disabled: true }],
      minStock: [{ value: '', disabled: true }],
      currentStock: [{ value: '', disabled: true }],
      movementType: [null, Validators.required],   // un solo control
      quantity: [null, [Validators.required, Validators.min(1)]],
      reason: ['', Validators.required]
    });

    this.valueChangesSub = this.movementForm.get('productId')!.valueChanges.subscribe(prodId => {
      if (prodId === null || prodId === '' || this.updatingFields) return;

      const product = this.products()?.find(p => p.id == prodId);
      if (product) {
        this.updatingFields = true;
        this.movementForm.patchValue({
          category: product.category,
          minStock: product.minStock,
          currentStock: product.currentStock
        });
        this.updatingFields = false;
      }
    });
  }


  onSubmit(): void {

    if (this.movementForm.invalid || this.isSubmitting()) {
      this.movementForm.markAllAsTouched();
      return;
    }

    const rawValue = this.movementForm.getRawValue(); // incluye campos desabilitados

    const movement: MovementRequest = {
      productId: rawValue.productId,
      type: rawValue.movementType,
      quantity: rawValue.quantity,
      reason: rawValue.reason
    };

    this.isSubmitting.set(true);

    this.subscription.add(
      this.inventoryStoreService.createMovement(movement).subscribe({
        next: (response) => {

          this.movementForm.reset();

          // this.movementForm.patchValue({
          //   category: '',
          //   minStock: '',
          //   currentStock: ''
          // });

          this.isSubmitting.set(false);
        },
        complete: () => {
          this.isSubmitting.set(false);
        }
      })
    );
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
    this.valueChangesSub?.unsubscribe();
  }

  onCancel(): void {
    this.movementForm.reset();
  }

}
