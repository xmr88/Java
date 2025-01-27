# ChatMate API

ChatMate API е RESTful приложение за управление на онлайн комуникация, наподобяваща популярни платформи като Slack, Discord и Microsoft Teams. Системата предоставя основни функционалности за управление на потребители, канали и съобщения.

## Функционалности

- Създаване на потребители и добавяне на приятели.
- Създаване и управление на канали.
- Изпращане на лични съобщения и съобщения в канали.
- Поддръжка на роли (Собственик и Администратор) за управление на канали.
- Страниране на списъци (канали, приятели, съобщения).
- Обработка на грешки с ясни статус кодове и съобщения.

---

## Технологии

- **Java 17**
- **Spring Boot** за REST API
- **Spring Data JPA** за работа с база данни
- **H2 база данни** (вградена)
- **Postman** за тестове и документация

---

## Как да стартираме проекта

### 1. Изисквания

- **Java 17** или по-нова версия
- **Maven** за управление на зависимостите

### 2. Стартиране на приложението

1. Клонирай проекта:
   ```bash
   git clone <линк към репозиторито>
   cd <папката на проекта>


Приложението ще бъде достъпно на:
URL: http://localhost:8080

Примерни API заявки

Потребители
Създаване на потребител

POST /api/users
Body:

{
  "username": "test_user"
}
Добавяне на приятел

POST /api/users/{id}/add-friend?friendId={friendId}

Връщане на всички приятели

GET /api/users/{id}/friends

Канали
Създаване на канал

POST /api/channels
Body:

{
  "name": "general",
  "ownerId": 1
}
Връщане на всички канали

GET /api/channels

Задаване на роля "Администратор"

POST /api/channels/{channelId}/owner/{ownerId}/assign-admin?userId={userId}

Премахване на гост от канал

DELETE /api/channels/{channelId}/owner/{ownerId}/remove-member/{memberId}

Съобщения
Изпращане на съобщение до приятел

POST /api/messages/friend
Body:

{
  "content": "Hello, how are you?",
  "authorId": 1,
  "recipientId": 2
}
Изпращане на съобщение в канал

POST /api/messages
Body:

{
  "content": "Hello, everyone!",
  "authorId": 1,
  "channelId": 1
}
Връщане на съобщения от канал (със страниране)

GET /api/messages/channel/{channelId}/paged?page=0&size=10

Връщане на съобщения между приятели (със страниране)

GET /api/messages/conversation/paged?userId1=1&userId2=2&page=0&size=10

Обработка на грешки

Приложението връща ясни статус кодове и съобщения при грешки:

400 Bad Request – Невалидни входни данни.
403 Forbidden – Достъпът е отказан (например, потребителят не е собственик или администратор).
404 Not Found – Ресурсът не е намерен.
500 Internal Server Error – Вътрешна грешка в приложението.
