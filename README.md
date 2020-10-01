# StockMarket
Project for iTechArt

Descriptions:

The service provides an opportunity to monitor changes in the stock market, collect and analyze information on companies' shares of interest.

#The service has the following features:
1.List of trading companies:

link:   localhost:8080/company/getAllCompanies

2.Company-specific data:

link:   localhost:8080/company/getCompany?ticker=TSLA

description: ticker - company ticker (REQUIRED).

3.Company specific news:

link:   localhost:8080/company/getCompanyNews?ticker=MCD&from=2019-01-01&to=2020-01-01

description: ticker - company's ticker (REQUIRED), from - date from which (REQUIRED), to - date until which (REQUIRED).

4.Data on shares of specific companies throughout the day:

link:   localhost:8080/stock/getCompanyStockForTheDay?ticker=MCD

description: ticker - company ticker (REQUIRED).

5.Complete 52-week stock data for specific companies:

link:   localhost:8080/stock/getCompanyStockForTheYear?ticker=AAPL

description: ticker - company ticker (REQUIRED).

6.Company-specific candlestick data:

link:   localhost:8080/stock/getStockCandles?ticker=MCD&from=1572651390&to=1572910590&resolution=1

description: ticker - company's ticker (REQUIRED), from - UNIX date from which (REQUIRED), to - UNIX date until which (REQUIRED), resolution - displays at intervals (1,5,15,30,60, D, W, M).

7.Financial reports of specific companies:

link:   localhost:8080/company/getFinancialReport?ticker=AAPL

description: ticker - company ticker (REQUIRED).

#