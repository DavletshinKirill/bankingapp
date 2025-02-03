# Statement Service

#### Statement Service - это микросервис прескоринга.

## Содержание

### POST: /statement

- По API приходит LoanStatementRequestDto
- На основе LoanStatementRequestDto происходит прескоринг.
- Отправляется POST-запрос на /deal/statement в МС deal через RestClient.
- Ответ на API - список из 4х LoanOfferDto от "худшего" к "лучшему".

### POST: /statement/offer

- По API приходит LoanOfferDto
- Отправляется POST-запрос на /deal/offer/select в МС deal через RestClient.

## Требования

- Java 17 или выше
- Maven или Gradle
- Spring Boot
- Springdoc для документации API
- Junit, Mockito для тестов


