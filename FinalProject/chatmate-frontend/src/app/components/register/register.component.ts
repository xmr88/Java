import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-register',
  standalone: true, // Standalone компонент
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
  imports: [FormsModule], // За [(ngModel)]
})
export class RegisterComponent {
  username = ''; // Поле за потребителско име

  constructor(private authService: AuthService, private router: Router) {}

  onSubmit(): void {
    if (this.username.trim() === '') {
      alert('Please enter a username.');
      return;
    }

    this.authService.register(this.username).subscribe({
      next: () => {
        alert('Registration successful!');
        this.router.navigate(['/login']); // Пренасочване към логин страницата
      },
      error: (err) => {
        console.error('Registration error:', err);
        alert('Registration failed. Please try again.');
      },
    });
  }
}
