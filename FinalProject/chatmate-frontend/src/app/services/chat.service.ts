import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

export interface Message {
  id: number;
  content: string;
  timestamp: string;
  authorId: number;
  channelId?: number;
  recipientId?: number;
}

@Injectable({
  providedIn: 'root',
})
export class ChatService {
  private baseUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  getChannels(): Observable<{ id: number; name: string }[]> {
    return this.http.get<{ id: number; name: string }[]>(`${this.baseUrl}/channels`);
  }

  getUsers(): Observable<{ id: number; username: string }[]> {
    return this.http.get<{ id: number; username: string }[]>(`${this.baseUrl}/users`);
  }

  getFriends(userId: number): Observable<{ id: number; username: string }[]> {
    return this.http.get<{ id: number; username: string }[]>(`${this.baseUrl}/users/${userId}/friends`);
  }

  getMessages(channelId: number): Observable<Message[]> {
    return this.http.get<{ id: number; content: string; authorId: number; timestamp: string }[]>(
      `${this.baseUrl}/messages/channel/${channelId}`
    ).pipe(
      map((messages) =>
        messages.map((msg) => ({
          ...msg,
          channelId: channelId,
        }))
      )
    );
  }

  sendMessage(sender: string, content: string, channelId: number): Observable<any> {
    const message = { content, authorId: sender, channelId };
    return this.http.post(`${this.baseUrl}/messages`, message);
  }

  sendMessageToUser(senderId: number, recipientId: number, content: string): Observable<any> {
    const message = { content, authorId: senderId, recipientId: recipientId };
    return this.http.post(`${this.baseUrl}/messages`, message);
  }

  addUserToChannel(channelId: number, userId: number): Observable<any> {
    return this.http.post(`${this.baseUrl}/channels/${channelId}/addMember?userId=${userId}`, {});
  }
}
