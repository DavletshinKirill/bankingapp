CREATE
DATABASE dea1;

create table client
(
    birth_date       DATE,
    dependent_amount INTEGER,
    client_id_uuid   UUID NOT NULL,
    account_number   VARCHAR(255),
    email            VARCHAR(255),
    first_name       VARCHAR(255),
    gender           VARCHAR(255) CHECK (gender IN ('MALE', 'FEMALE', 'NOT_BINARY')),
    last_name        VARCHAR(255),
    marital_status   VARCHAR(255) CHECK (marital_status IN ('MARRIED', 'DIVORCED', 'SINGLE', 'WIDOW_WIDOWER')),
    middle_name      VARCHAR(255),
    employment_id    JSONB,
    passport_id      JSONB,
    PRIMARY KEY (client_id_uuid),
    CHECK (employment_id IS NULL OR
           ((cast(employment_id ->>'status' AS SMALLINT) IS NULL OR cast(employment_id ->>'status' AS SMALLINT) BETWEEN 0 AND 3) AND
            (cast(employment_id ->>'position' AS SMALLINT) IS NULL OR cast(employment_id ->>'position' AS SMALLINT) BETWEEN 0 AND 3)))
);

create table credit
(
    amount            NUMERIC(38, 2),
    insurance_enabled BOOLEAN,
    monthly_payment   NUMERIC(38, 2),
    psk               NUMERIC(38, 2),
    rate              NUMERIC(38, 2),
    salary_client     BOOLEAN,
    term              INTEGER NOT NULL,
    credit_id_uuid    UUID    NOT NULL,
    credit_status     VARCHAR(255) CHECK (credit_status IN ('CALCULATED', 'ISSUED')),
    payment_schedule  JSONB,
    PRIMARY KEY (credit_id_uuid)
);

create table statement
(
    creation_date     TIMESTAMP(6),
    sign_date         TIMESTAMP(6),
    client_id         UUID UNIQUE,
    credit_id         UUID UNIQUE,
    statement_id_uuid UUID NOT NULL,
    ses_code          VARCHAR(255),
    status            VARCHAR(255) CHECK (status IN
                                          ('PREAPPROVAL', 'APPROVED', 'CC_DENIED', 'CC_APPROVED', 'PREPARE_DOCUMENTS',
                                           'DOCUMENT_CREATED', 'DOCUMENT_SIGNED', 'CREDIT_ISSUED')),
    applied_offer     JSONB,
    PRIMARY KEY (statement_id_uuid)
)