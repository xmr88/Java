import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms'; // Импортиране на FormsModule за [(ngModel)]

@Component({
  selector: 'app-login',
  standalone: true, // Standalone компонент
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  imports: [FormsModule], // Добавяне на FormsModule
})
export class LoginComponent {
  username = ''; // Поле за потребителско име

  onSubmit(): void {
    if (this.username.trim() === '') {
      alert('Please enter a username.');
      return;
    }
    alert(`Login successful for user: ${this.username}`);
  }
}
