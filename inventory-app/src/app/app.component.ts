import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HeaderComponent } from './shared/components/header/header.component';
import { SidebarComponent } from './shared/components/sidebar/sidebar.component';
import { ToastComponent } from "./shared/components/toast/toast.component";

@Component({
  selector: 'app-root',
  standalone: true,
  templateUrl: './app.component.html',
  imports: [HeaderComponent, SidebarComponent, RouterOutlet, ToastComponent],
})
export class AppComponent {
  title = 'inventory-app';
}
