# Deal Service

#### Calculator Service осуществляет расчёт возможных условий кредита, выбор одного из предложений, завершение регистрации + полный подсчёт кредита.

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

## Требования

- Java 17 или выше
- Maven или Gradle
- Spring Boot
- Springdoc для документации API
- Junit, Mockito для тестов
- СУБД Postgres 