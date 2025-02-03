# Gateway Service

#### Gateway Service - это микросервис реализующий паттерн API-Gateway для сервисов кредитного конвейера. Суть паттерна заключается в том, что клиент отправляется запросы не в несколько разных МС с бизнес-логикой, а только в один МС (gateway), чтобы он уже перенаправлял запросы в другие МСы. Главная задача этого МС - инкапсулировать сложную логику всей внутренней системы, предоставив клиенту простой и понятный API.

## Содержание

### Сalculator
- POST: /calculator/offers - расчёт возможных условий кредита.
- POST: /calculator/calc - валидация присланных данных + полный расчет параметров кредита

### Deal
- POST: /deal/statement - расчёт возможных условий кредита
- POST: /deal/offer/select - выбор одного из предложений
- POST: /deal/calculate/{statementId} -  полный расчет параметров кредита
- POST: /deal/document/{statementId}/send - запрос на отправку документов
- POST: /deal/document/{statementId}/sign - запрос на подписание документов
- POST: /deal/document/{statementId}/code - подписание документов
- GET: /deal/admin/statement/{statementId} - получить заявку по id
- PUT: /deal/admin/statement/{statementId}/status - обновить статус заявки
 
### Statement
- POST: /statement - первичная валидация заявки, создание заявки
- POST: /statement/offer - выбор одного из предложений

## Требования

- Java 17 или выше
- Maven или Gradle
- Spring Boot
- Springdoc для документации API
- Junit, Mockito для тестов


