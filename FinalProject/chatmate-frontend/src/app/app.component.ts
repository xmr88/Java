import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet], // Импортиране на RouterOutlet за маршрутизация
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'], // Множество стилове или само един
})
export class AppComponent {
  title = 'chatmate-frontend'; 
}
