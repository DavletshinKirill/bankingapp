# Deal Service

#### Deal Service осуществляет расчёт возможных условий кредита, выбор одного из предложений, завершение регистрации + полный подсчёт кредита.

## Содержание

### POST: /deal/statement

- По API приходит LoanStatementRequestDto.
- На основе LoanStatementRequestDto создаётся сущность Client и сохраняется в БД.
- Создаётся Statement со связью на только что созданный Client и сохраняется в БД.
- Отправляется POST запрос на /calculator/offers МС Калькулятор через RestClient
  Каждому элементу из списка List<LoanOfferDto> присваивается id созданной заявки (Statement).
- Ответ на API - список из 4х LoanOfferDto от "худшего" к "лучшему".

### POST: /deal/offer/select

- По API приходит LoanOfferDto
- Достаётся из БД заявка(Statement) по statementId из LoanOfferDto.
- В заявке обновляется статус, история статусов(List<StatementStatusHistoryDto>), принятое предложение LoanOfferDto
  устанавливается в поле appliedOffer.
- Заявка сохраняется.

### POST: /deal/calculate/{statementId}

- По API приходит объект FinishRegistrationRequestDto и параметр statementId (String).
- Достаётся из БД заявка(Statement) по statementId.
- ScoringDataDto насыщается информацией из FinishRegistrationRequestDto и Client, который хранится в Statement.
  -Отправляется POST запрос на /calculator/calc МС Калькулятор с телом ScoringDataDto через RestClient.
- На основе полученного из кредитного конвейера CreditDto создаётся сущность Credit и сохраняется в базу со статусом
  CALCULATED.
- В заявке обновляется статус, история статусов.
- Заявка сохраняется.

### Добавлено API в МС-deal

- POST: /deal/document/{statementId}/send - запрос на отправку документов.
- POST: /deal/document/{statementId}/sign - запрос на подписание документов
- POST: /deal/document/{statementId}/code - подписание документов
- Первая отправка письма на почту клиенту должна происходить в самом конце существующей API POST: /deal/offer

### Реализовано взаимодействие через Kafka между МС-deal и МС-dossier

1. Kafka и Zookeeper следует поднять через docker-compose
2. МС-deal выступает в роли издателя (producer), МС-dossier в роли подписчика (consumer)
3. Завести в кафке 6 топиков, соответствующие темам, по которым необходимо направить письмо на почту Клиенту:
- finish-registration
- create-documents
- send-documents
- send-ses
- credit-issued
- statement-denied

### Добавлено админское API

- GET: /deal/admin/statement/{statementId} - получить заявку по id
- GET: /deal/admin/statement - получить все заявки

## Требования

- Java 17 или выше
- Maven или Gradle
- Spring Boot
- Springdoc для документации API
- Junit, Mockito для тестов
- СУБД Postgres
- Kafka и Zookeeper для обмена сообщениями