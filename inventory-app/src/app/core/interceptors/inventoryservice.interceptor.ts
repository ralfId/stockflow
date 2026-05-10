import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { catchError, throwError } from 'rxjs';
import { ToastService } from 'src/app/services/toas-service/toast-service.service';
import { ErrorResponse } from '../models/error-response.model';

export const InventoryServiceInterceptor: HttpInterceptorFn = (req, next) => {

  const toastService = inject(ToastService);

  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      let errorMessage = 'Ha ocurrido un error inesperado';

      if (error.error instanceof ErrorEvent) {
        // error la cliente
        errorMessage = `Error: ${error.error.message}`;
        toastService.showError(errorMessage, errorMessage);
      } else {
        // error del api
        const apiError = error.error as ErrorResponse;

        errorMessage = parseBackendError(apiError.status);
        toastService.showError(errorMessage, apiError.message);
      }
      // se relanza por si se necesita
      return throwError(() => error);
    })
  );

  // Función auxiliar para extraer el mensaje del backend
  function parseBackendError(error: number): string {
    // Manejo por códigos de estado si no hay mensaje en el body
    switch (error) {
      case 400: return 'Petición incorrecta';
      case 401: return 'Sesión expirada o no autorizado';
      case 403: return 'No tienes permisos para esta acción';
      case 404: return 'El recurso solicitado no existe';
      case 500: return 'Error interno';
      default: return 'Error desconocido';
    }
  }

}
