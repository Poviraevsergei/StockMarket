INSERT INTO USER (login, password, email, created, changed)
VALUES ('poviraev', 'pass1', 'poviraevsergei@gmail.com', '1970-01-01 00:00:01', '1970-01-01 00:00:01'),
       ('Carl', 'pass2', 'poviraevsergei@gmail.com', '1970-01-01 00:00:01', '1970-01-01 00:00:01'),
       ('Sasha', 'pass3', 'email3', '1970-01-01 00:00:01', '1970-01-01 00:00:01');

INSERT INTO ROLE (is_active, expiry_date)
VALUES (true, '2020-10-14'),
       (true, '2020-10-13'),
       (true, '2021-10-10');

INSERT INTO COMPANY (name, ticker, industry, phone, url, logo)
VALUES ('Apple Inc', 'AAPL', 'Technology', '14089961010', 'https://www.apple.com/',
        'https://static.finnhub.io/logo/87cb30d8-80df-11ea-8951-00000000092a.png'),
       ('Epam Systems Inc', 'EPAM', 'Technology', '12677599000', 'https://www.epam.com/',
        'https://static.finnhub.io/logo/a0c66bba-80cb-11ea-bcdf-00000000092a.png'),
       ('Tesla Inc', 'TSLA', 'Automobiles', '16506815000', 'https://www.tesla.com/',
        'https://static.finnhub.io/logo/2dd96524-80c9-11ea-aaac-00000000092a.png');

INSERT INTO STOCK (company, open_price, close_price, ticker, date, annual_dividends, lowest_annual_price,
                   highest_annual_price)
VALUES ('1', '113.92', '113.02', 'AAPL', '2020-10-05', '0.75', '53.1525', '137.98'),
       ('2', '330.34', '326.92', 'EPAM', '2020-10-05', '-', '151.97', '338.91'),
       ('3', '423.35', '415.09', 'TSLA', '2020-10-05', '-', '44.856', '502.49');

INSERT INTO user_company (user_id, company_id)
VALUES ('1', '1'),
       ('1', '2'),
       ('3', '1');
