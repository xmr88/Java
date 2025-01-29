import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private baseUrl = 'http://localhost:8080/api'; // Бекенд адрес

  constructor(private http: HttpClient) {}

  login(username: string): Observable<any> {
    return this.http.post(`${this.baseUrl}/users`, { username });
  }

  register(username: string): Observable<any> {
    return this.http.post(`${this.baseUrl}/register`, { username });
  }
}
