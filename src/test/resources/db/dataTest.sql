INSERT INTO USER (email, created, changed)
VALUES ('stockitechart@gmail.com', '1970-01-01 00:00:01', '1970-01-01 00:00:01'),
       ('randomMail@gmail.com', '1970-01-01 00:00:01', '1970-01-01 00:00:01'),
       ('randomMail2@gmail.com', '1970-01-01 00:00:01', '1970-01-01 00:00:01');

INSERT INTO STATUS (is_active, expiry_date)
VALUES (false, '2020-10-10'),
       (true, '2021-10-12'),
       (false, '2020-10-11');

INSERT INTO SECURITY (login, password, user_role)
VALUES ('admin', '$2a$12$UjzEp7t3QZBsPBzUI8GmAO8p0Hn4xySRwDObzWHBks6PNSTetHK2i', 'ROLE_ADMIN'),
       ('client', '$2a$12$UjzEp7t3QZBsPBzUI8GmAO8p0Hn4xySRwDObzWHBks6PNSTetHK2i', 'ROLE_CLIENT'),
       ('user', '$2a$12$UjzEp7t3QZBsPBzUI8GmAO8p0Hn4xySRwDObzWHBks6PNSTetHK2i', 'ROLE_USER');

INSERT INTO COMPANY (name, ticker, industry, phone, url, logo)
VALUES ('Apple Inc', 'AAPL', 'Technology', '14089961010', 'https://www.apple.com/',
        'https://static.finnhub.io/logo/87cb30d8-80df-11ea-8951-00000000092a.png'),
       ('Mcdonalds Corp', 'MCD', 'Hotels, Restaurants & Leisure', '16306233000', 'https://www.mcdonalds.com/us/en-us.html',
        ''),
       ('Epam Systems Inc', 'EPAM', 'Technology', '12677599000', 'https://www.epam.com/',
        'https://static.finnhub.io/logo/a0c66bba-80cb-11ea-bcdf-00000000092a.png'),
       ('Tesla Inc', 'TSLA', 'Automobiles', '16506815000', 'https://www.tesla.com/',
        'https://static.finnhub.io/logo/2dd96524-80c9-11ea-aaac-00000000092a.png');

INSERT INTO STOCK (company, open_price, close_price, ticker, date, annual_dividends, lowest_annual_price,
                   highest_annual_price)
VALUES ('1', '113.92', '113.02', 'AAPL', '2019-11-04', '0.75', '53.1525', '137.98'),
       ('1', '123.32', '213.02', 'AAPL', '2020-11-04', '0.75', '53.1525', '137.98'),
       ('2', '223.32', '213.02', 'MCD', '2020-11-04', '0.34', '53.1525', '137.98'),
       ('2', '323.32', '213.02', 'MCD', '2020-11-04', '0.35', '53.1525', '137.98'),
       ('3', '330.34', '326.92', 'EPAM', '2019-11-04', '-', '151.97', '338.91'),
       ('3', '230.34', '226.92', 'EPAM', '2020-11-04', '-', '251.97', '238.91'),
       ('4', '423.35', '415.09', 'TSLA', '2019-11-04', '-', '44.856', '502.49'),
       ('4', '223.35', '215.09', 'TSLA', '2020-11-04', '-', '44.856', '502.49');

INSERT INTO user_company (user_id, company_id)
VALUES ('1', '1'),
       ('1', '2'),
       ('2', '4'),
       ('3', '1'),
       ('3', '2');
