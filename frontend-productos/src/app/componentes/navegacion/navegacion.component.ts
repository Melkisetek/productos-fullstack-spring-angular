import { Component, inject } from '@angular/core';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-navegacion',
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './navegacion.component.html',
  styleUrl: './navegacion.component.css',
})
export class NavegacionComponent {
  private authService = inject(AuthService);
  private router = inject(Router);

  isAutenticado(){
    return this.authService.isAuthenticated();
  }

  salir() {
    this.authService.logout();
    
  }
}
