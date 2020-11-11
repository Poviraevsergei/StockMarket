# StockMarket
Project for iTechArt

Descriptions:

The service provides an opportunity to monitor changes in the stock market, collect and analyze information on companies' shares of interest.

# Tests
Before starting to use the application, it is recommended to run tests to check its functionality.
 This can be done by selecting the StockMarket-> src-> test-> java folder and pressing Ctrl + Shift + F10, or by right-clicking and selecting “Run ‘All Tests ’”.

# Database
The in memory database h2 is connected to the project. You can connect to it by going to "http://localhost:8080/h2-console/". It contains 5 tables.
User - stores information about users. At the time of creation, it has 3 users.
Company - stores data about the companies being monitored. At the time of creation, it has 4 companies.
Stock - Stores daily data on stocks of companies from the Company table. IMPORTANT: For a correct check of OpenApi operation, you need to add the data of the ‘data’ column of the ‘data.sql’ file so that it corresponds to the present day.
Security - Stores data about the login, password (in encrypted form), user data for his identification.
Status - stores access data for using paid resources and applications.

# Registration
 To register, you can go through OAuth (Google) login, or go to "http://localhost:8080/registration". 
 When using the first method, the user is saved to the database with ROLE_USER and a random password is assigned to him. 
 A message with a username and password is sent to him by mail. The password can be changed by going to "http://localhost:8080/changePassword" and passing in the new password.
 ( Example: "http://localhost:8080/changePassword?NewPassword=pass" )
 
 Using the second method, we must pass json format ( POST method: {"email": "testMail@gmail.com", "login": "testLogin", "password": "testPassword"} )
 to "http://localhost:8080/registration" after which the user will be created and placed in the database.
 A message with a username and password will be sent to the mail.

# Authentication
Get access to endpoints (except:
  "http://localhost:8080/registration",
"http://localhost:8080/h2-console/",
"http://localhost:8080/",
"http://localhost:8080/login",
"http://localhost:8080/logout"
) we can only pass authentication. 
After passing the authentication, the user has the role USER, CLIENT or ADMIN. 
The database contains 3 users with different roles: (Login: admin, Password: password),
 (Login: client, Password: password),
  (Login: user, Password: password);


# User capabilities
___
- ROLE_USER - user who does not use paid add-ons.


Available endpoints:


- "http://localhost:8080/changePassword?newPassword={password}" – change password.
 
 Example:( GET method: "http://localhost:8080/changePassword?newPassword=pass" )

- "http://localhost:8080/company/getCompanyFromDb/{ticker}" – get company data from database.

Example:( GET method: "http://localhost:8080/company/getCompanyFromDb/TSLA" )

- "http://localhost:8080/company/getAllCompaniesFromDb" - get data for all companies in the database.

Example:( GET method: "http://localhost:8080/company/getAllCompaniesFromDb" )

- "http://localhost:8080/company/getAllCompanies" – get data on all companies trading shares.

Example:( GET method: "http://localhost:8080/company/getAllCompanies" )

- "http://localhost:8080/company/getCompany/{ticker}" – get data about company from ticker.

Example:( GET method: "http://localhost:8080/company/getCompany/TSLA" )

- "http://localhost:8080/company/getCompanyNews" – get the latest news on global companies.

Example:( GET method: "http://localhost:8080/company/getCompanyNews" )

- "http://localhost:8080/company/getFinancialReport/{ticker}" – receive financial statements of the company.

Example:( GET method: "http://localhost:8080/company/getFinancialReport/TSLA" )

- "http://localhost:8080/stock/getStock?date={date}&ticker={ticker}" – get data on a stock for a specific date from the database.

Example:( GET method: "http://localhost:8080/stock/getStock?date=2019-11-04&ticker=EPAM" )

- "http://localhost:8080/stock/getStockCandles?ticker={ticker}&from={date_from}&to={date_to}&resolution={step(D-default)}’ – get data on candlesticks of companies for a certain period.

Example:( GET method: "localhost:8080/stock/getStockCandles?ticker=IBM&from=1572651390&to=1575243390&resolution=D" )

- "http://localhost:8080/stock/getCompanyStockForTheDay?ticker={ticker}" – get data on a stock to date from the database.

Example:( GET method: "http://localhost:8080/stock/getCompanyStockForTheDay?ticker=TSLA" )

- "http://localhost:8080/stock/getCompanyStockForTheYear?ticker={ticker}" – get stock data for a year from the database.

Example:( GET method: "http://localhost:8080/stock/getCompanyStockForTheYear?ticker=TSLA" )

- "http://localhost:8080/user/addCompany?ticker={ticker}" – add companies to your personal tracking profile. (ROLE_USER can track up to 3 companies).

Example:( GET method: "http://localhost:8080/user/addCompany?ticker=TSLA" )

___
- ROLE_CLIENT - user using paid add-ons.

 Available endpoints:
 
  - "http://localhost:8080/changePassword?newPassword={password}" – change password.
  
 Example:( GET method: "http://localhost:8080/changePassword?newPassword=pass" )
 
 - "http://localhost:8080/comparison?firstTicker={ticker}&secondTicker={ticker}" – comparison of shares of two companies with each other.
 
 Example:( GET method: "http://localhost:8080/comparison?firstTicker=TSLA&secondTicker=EPAM" )
 
 - "http://localhost:8080"/comparisonForTheYear?ticker={ticker}" – comparison of a company's stock with a difference per year.
 
 Example:( GET method: "http://localhost:8080/comparisonForTheYear?ticker=EPAM" )
 
 - "http://localhost:8080/company/getCompanyFromDb/{ticker}" – get company data from database. 
 
 Example:( GET method: "http://localhost:8080/company/getCompanyFromDb/TSLA" )
 
 - "http://localhost:8080/company/getAllCompaniesFromDb" – get data for all companies in the database.
 
 Example:( GET method: "http://localhost:8080/company/getAllCompaniesFromDb" )
 
 - "http://localhost:8080/company/getAllCompanies" – get data on all companies trading shares.
 
 Example:( GET method: "http://localhost:8080/company/getAllCompanies" )
 
 - "http://localhost:8080/company/getCompany/{ticker} – get data on a specific company.
 
 Example:( GET method: "http://localhost:8080/company/getCompany/TSLA" ) 
 
 - "http://localhost:8080/company/getCompanyNews" – get the latest news on global companies.
 
 Example:( GET method: "http://localhost:8080/company/getCompanyNews" )
 
 - "http://localhost:8080/company/getFinancialReport/{ticker}" – receive financial statements of the company.
 
 Example:( GET method: "http://localhost:8080/company/getFinancialReport/TSLA" )
 
 - "http://localhost:8080/stock/getStock?date={date}&ticker={ticker}" – get data on a stock for a specific date from the database.
 
 Example:( GET method: "http://localhost:8080/stock/getStock?date=2019-11-04&ticker=EPAM" )
 
 - "http://localhost:8080/stock/getStockCandles?ticker={ticker}&from={date_from}&to={date_to}&resolution={step(D-default)}" – get data on candlesticks of companies for a certain period.
 
 Example:( GET method: "http://localhost:8080/stock/getStockCandles?ticker=IBM&from=1572651390&to=1575243390&resolution=D" )
 
 - "http://localhost:8080/stock/getCompanyStockForTheDay?ticker={ticker}" – get data on a stock to date from the database.
 
 Example:( GET method: "http://localhost:8080/stock/getCompanyStockForTheDay?ticker=TSLA" )
 
 - "http://localhost:8080/stock/getCompanyStockForTheYear?ticker={ticker}" – get stock data for a year from the database.
 
 Example:( GET method: "http://localhost:8080/stock/getCompanyStockForTheYear?ticker=TSLA" )
 
 - "http://localhost:8080/user/addCompany?ticker={ticker}" – add companies to your personal tracking profile. (ROLE_CLIENT can use an unlimited count of companies).
 
 Example:( GET method: "http://localhost:8080/user/addCompany?ticker=TSLA" )

___
- ROLE_ADMIN - has access to all service features.

 Available endpoints:

- "http://localhost:8080/user/findAll" – get data for all users saved in the system.

Example: ( GET method: "http://localhost:8080/user/findAll" ) 

- "http://localhost:8080/user/{id}" – get data for a user with a specific id. 

Example: ( GET method: "http://localhost:8080/user/1" ) 

- "http://localhost:8080/user/addCompany?ticker={ticker}" – get data for a user with a specific id. 

Example: ( GET method: "http://localhost:8080/user/addCompany?ticker=TSLA" ) 

- "http://localhost:8080/user/create" – create user. To do this, you need to pass json format:(   {"email":"testMail@gmail.com",
   "login":"testLogin",
      "password":"testPassword"
   })
   
Example: ( POST method: "http://localhost:8080/user/create" ) 

- "http://localhost:8080/user/update" – update user. To do this, you need to pass json format:( {"id": 2,"status": {"id": 2,"isActive": true,"expiryDate": "2021-10-12"},"security": { "id": 2,
 "login": "client","password":"$2a$12$UjzEp7t3QZBsPBzUI8GmAO8p0Hn4xySRwDObzWHBks6PNSTetHK2i","userRole": "ROLE_CLIENT"},"email": "newEmail@gmail.com","created": "2020-01-01","changed": "2020-01-01","userCompanies": []} )

Example: ( PUT method: "http://localhost:8080/user/update" ) 

- "http://localhost:8080/user/{id}" – delete user data from the database with a specific id. 

Example: ( DELETE method: "http://localhost:8080/user/1" ) 

- "http://localhost:8080/company/getCompanyFromDb/{ticker}" – get company data from database. 

Example:( GET method: "http://localhost:8080/company/getCompanyFromDb/TSLA" )

- "http://localhost:8080/company/getAllCompaniesFromDb" – get data for all companies in the database.

Example:( GET method: "http://localhost:8080/company/getAllCompaniesFromDb" )

- "http://localhost:8080/company/getAllCompanies" – get data on all companies trading shares.

Example:( GET method: "http://localhost:8080/company/getAllCompanies" )

- "http://localhost:8080/company/getCompany/{ticker} – get data on a specific company.

Example:( GET method: "http://localhost:8080/company/getCompany/TSLA" )

- "http://localhost:8080/company/getCompanyNews" – get the latest news on global companies.

Example:( GET method: "http://localhost:8080/company/getCompanyNews" )

- "http://localhost:8080/company/getFinancialReport/{ticker}" – receive financial statements of the company.

Example:( GET method: "http://localhost:8080/company/getFinancialReport/TSLA" )

- "http://localhost:8080/company/{id}" – get data on a company with a specific id. 

Example: ( GET method: "http://localhost:8080/company/1" ) 

- "http://localhost:8080/company/create’ – create a company. To do this, you need to pass json format:( {
    "name": "New Company",
    "ticker": "NEW",
    "industry": "",
    "phone": "",
    "url": "",
    "logo": "",
    "companyStockes": []
} )

Example: ( POST method: "http://localhost:8080/company/create" )

- "http://localhost:8080/company/update" – update company data. To do this, you need to pass json format:( {
    "id": 2,
    "name": "Update Company",
    "ticker": "NEW",
    "industry": "",
    "phone": "",
    "url": "",
    "logo": "",
    "companyStockes": []
} )

Example: ( PUT method: "http://localhost:8080/company/update" )

- "http://localhost:8080/company/{id}" – delete user data from the database with a specific id.

Example: ( DELETE method: "http://localhost:8080/company/1" )
 
- "http://localhost:8080/stock/getStock?date={date}&ticker={ticker}" – get data on a stock for a specific date from the database.

Example:( GET method: "http://localhost:8080/stock/getStock?date=2019-11-04&ticker=EPAM" )

- "http://localhost:8080/stock/getAllStocks" – get data on all stocks from the database.

Example:( GET method: "http://localhost:8080/stock/getAllStocks" )

- "http://localhost:8080/stock/getStockCandles?ticker={ticker}&from={date_from}&to={date_to}&resolution={step(D-default)}" – get data on candlesticks of companies for a certain period.

Example:( GET method: "localhost:8080/stock/getStockCandles?ticker=IBM&from=1572651390&to=1575243390&resolution=D" )

- "http://localhost:8080/stock/getCompanyStockForTheDay?ticker={ticker}" – get data on a stock to date from the database.

Example:( GET method: "http://localhost:8080/stock/getCompanyStockForTheDay?ticker=TSLA" )

- "http://localhost:8080/stock/getCompanyStockForTheYear?ticker={ticker}" – get stock data for a year from the database.

Example:( GET method: "http://localhost:8080/stock/getCompanyStockForTheYear?ticker=TSLA" )

- "http://localhost:8080/stock/create" – create a stock. To do this, you need to pass json format:( {
	"company": {
        "id": 1,
        "name": "Apple Inc",
        "ticker": "AAPL",
        "industry": "Technology",
        "phone": "14089961010",
        "url": "https://www.apple.com/",
        "logo": "https://static.finnhub.io/logo/87cb30d8-80df-11ea-8951-00000000092a.png",
        "companyStockes": []
    },
        "openPrice": "",
        "closePrice": "",
        "ticker": "AAPL",
        "date": "2019-11-05",
        "annualDividends": "",
        "lowestAnnualPrice": "",
        "highestAnnualPrice": ""
    })
Example: (POST method: ‘http://localhost:8080/stock/create’ ) 
- "http://localhost:8080/stock/updatee"– обновить данные акции. Для этого нужно передать json формата:( {
	"company": {
        "id": 1,
        "name": "Apple Inc",
        "ticker": "AAPL",
        "industry": "Technology",
        "phone": "14089961010",
        "url": "https://www.apple.com/",
        "logo": "https://static.finnhub.io/logo/87cb30d8-80df-11ea-8951-00000000092a.png",
        "companyStockes": []
    },
        "openPrice": "",
        "closePrice": "",
        "ticker": "AAPL",
        "date": "2019-11-05",
        "annualDividends": "",
        "lowestAnnualPrice": "",
        "highestAnnualPrice": ""
    } )
    
Example: ( PUT method: "http://localhost:8080/stock/update" )

- "http://localhost:8080/stock/{id}e"– delete stock data from the database with a specific id.

Example: ( DELETE method: "http://localhost:8080/stock/5" )
 
- "http://localhost:8080/comparison?firstTicker={ticker}&secondTicker={ticker}"– comparison of shares of two companies with each other.

Example:( GET method: "http://localhost:8080/comparison?firstTicker=TSLA&secondTicker=EPAM" )

- "http://localhost:8080"/comparisonForTheYear?ticker={ticker}"– comparison of a company's stock with a difference per year.

Example:( GET method: "http://localhost:8080/comparisonForTheYear?ticker=EPAM" )

- "http://localhost:8080/changePassword?newPassword={password}"– change password.

Example:( GET method: "http://localhost:8080/changePassword?newPassword=pass")

- "http://localhost:8080/changeToPremium?userLogin={userLogin}"– change role from ROLE_USER to ROLE_CLIENT.

Example:( GET method: "http://localhost:8080/changeToPremium?userLogin=user")

# Additional features

Once a day, the application updates data on shares of companies in the database. 
It also checks users once a day for the expiration of a paid subscription. 
During the last three days, the application sends messages to the user's mail with a reminder of the expiration of the paid version, and then switches to the free version and changes the role from ROLE_CLIENT to ROLE_USER.
