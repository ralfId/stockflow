import { bootstrapApplication } from '@angular/platform-browser';
import { provideRouter } from '@angular/router';

import { AppComponent } from './app/app.component';
import { appRoutes } from './app/routes/app.routes';
import { provideAnimations } from '@angular/platform-browser/animations';
import { provideHttpClient, withFetch, withInterceptors } from '@angular/common/http';
import { InventoryServiceInterceptor } from './app/core/interceptors/inventoryservice.interceptor';

bootstrapApplication(AppComponent, {
  providers: [
    provideRouter(appRoutes),
    provideAnimations(),
    provideHttpClient(withFetch(),withInterceptors([InventoryServiceInterceptor])),
  ],
}).catch((err) => console.error(err));
