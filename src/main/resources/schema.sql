DROP TABLE IF EXISTS USER;
DROP TABLE IF EXISTS COMPANY;
DROP TABLE IF EXISTS STOCK;

CREATE TABLE USER
(
    id       INT AUTO_INCREMENT PRIMARY KEY,
    login    VARCHAR(250) NOT NULL,
    password VARCHAR(250) NOT NULL,
    role     VARCHAR(250) NOT NULL,
    email    VARCHAR(250) NOT NULL,
    created  TIMESTAMP,
    changed  TIMESTAMP,
    deleted  BIT
);

CREATE TABLE COMPANY
(
    id       INT AUTO_INCREMENT PRIMARY KEY,
    name     VARCHAR(250) NOT NULL,
    ticker   VARCHAR(250) NOT NULL,
    industry VARCHAR(250) NOT NULL,
    phone    VARCHAR(250) NOT NULL,
    url      VARCHAR(250) NOT NULL,
    logo     VARCHAR(250) NOT NULL
);

CREATE TABLE STOCK
(
    id                   INT AUTO_INCREMENT PRIMARY KEY,
    company              INT NOT NULL references COMPANY on update cascade on delete cascade,
    open_price           VARCHAR(250) NOT NULL,
    close_price          VARCHAR(250) NOT NULL,
    ticker               VARCHAR(250) NOT NULL,
    date                 date         NOT NULL,
    annual_dividends     VARCHAR(250) NOT NULL,
    lowest_annual_price  VARCHAR(250) NOT NULL,
    highest_annual_price VARCHAR(250) NOT NULL
);

CREATE TABLE USER_COMPANY
(
    user_id    INT8 not null references USER on update cascade on delete cascade,
    company_id INT8 NOT NULL references COMPANY on update cascade on delete cascade,
    primary key (user_id, company_id)
);