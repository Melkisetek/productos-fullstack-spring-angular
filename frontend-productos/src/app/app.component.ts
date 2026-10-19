import { Component, inject } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { NavegacionComponent } from './componentes/navegacion/navegacion.component';

@Component({
  selector: 'app-root',
  imports: [RouterModule, NavegacionComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent {
  
}
