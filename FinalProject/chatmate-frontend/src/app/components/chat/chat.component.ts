import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ChatService, Message } from '../../services/chat.service';

@Component({
  selector: 'app-chat',
  standalone: true,
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css'],
  imports: [CommonModule, FormsModule],
})
export class ChatComponent implements OnInit {
  channels: { id: number; name: string }[] = [];
  users: { id: number; username: string }[] = [];
  friends: { id: number; username: string }[] = [];
  selectedChannel = '';
  selectedUserId!: number;
  selectedFriendId!: number;
  messages: Message[] = [];
  newMessage = '';
  currentUserId = 1; // Текущо влязъл потребител (временно зададено)

  constructor(private chatService: ChatService) {}

  ngOnInit(): void {
    this.loadChannels();
    this.loadUsers();
    this.loadFriends();
  }

  loadChannels(): void {
    this.chatService.getChannels().subscribe({
      next: (channels) => {
        this.channels = channels;
        if (channels.length > 0) {
          this.selectedChannel = channels[0].name;
          this.loadMessages(channels[0].id);
        }
      },
      error: (err) => console.error('Error loading channels:', err),
    });
  }

  loadUsers(): void {
    this.chatService.getUsers().subscribe({
      next: (data) => {
        this.users = data;
      },
      error: (err) => console.error('Error loading users:', err),
    });
  }

  loadFriends(): void {
    this.chatService.getFriends(this.currentUserId).subscribe({
      next: (data) => {
        this.friends = data;
      },
      error: (err) => console.error('Error loading friends:', err),
    });
  }

  loadMessages(channelId: number): void {
    this.chatService.getMessages(channelId).subscribe({
      next: (data: Message[]) => {
        this.messages = data.map((message) => ({
          ...message,
          sender: `User ${message.authorId}`,
        }));
      },
      error: (err) => console.error('Error loading messages:', err),
    });
  }

  sendMessage(): void {
    if (this.newMessage.trim() === '') {
      return;
    }

    const channelId = this.getChannelId(this.selectedChannel);
    this.chatService.sendMessage('User', this.newMessage, channelId).subscribe({
      next: () => {
        this.messages.push({
          id: Date.now(),
          content: this.newMessage,
          timestamp: new Date().toISOString(),
          authorId: 0,
          channelId: channelId,
        });
        this.newMessage = '';
      },
      error: (err) => console.error('Error sending message:', err),
    });
  }

  sendMessageToFriend(): void {
    if (!this.selectedFriendId || this.newMessage.trim() === '') {
      console.error('No friend selected or empty message');
      return;
    }

    this.chatService.sendMessageToUser(this.currentUserId, this.selectedFriendId, this.newMessage).subscribe({
      next: () => {
        console.log('Message sent to friend');
        alert('Message sent successfully!');
        this.newMessage = '';
      },
      error: (err) => console.error('Error sending message:', err),
    });
  }

  addUser(): void {
    if (!this.selectedUserId) {
      console.error('No user selected');
      return;
    }

    const channelId = this.getChannelId(this.selectedChannel);

    this.chatService.addUserToChannel(channelId, this.selectedUserId).subscribe({
      next: () => {
        console.log('User added to channel');
        alert('User added successfully!');
      },
      error: (err) => console.error('Error adding user:', err),
    });
  }

  getChannelId(channelName: string): number {
    const channel = this.channels.find((ch) => ch.name === channelName);
    return channel ? channel.id : 1;
  }
}
