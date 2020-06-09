# StockFeedSystem 
 This application is based on stock marget exchange user will buy or sell the stock according to the latestPrice.
 
 A company is planning to make a system for handling and processing of
Live Stock feeding and portfolio CRUD. Feeds of stock quotes could be in
any file format for example excel, text, pdf. Feeds data can be arrived at
any time and at any frequency. System should be able to process that.
User should be able to sell/purchase live stocks though brokerage and
should be able to calculate profit and loss.
Details :
1. Master data creation. Creation of Feed Management system which
will process the feed and persist it in DB.
2. Creation of User Management and sale/purchase of stocks.
3. Portfolio management and dashboard.
4. Profit and loss calculation based on latest feed for each user.

Consideration : Use reactive programming , JAVA 8 , no sql database
(Couchbase), Spring boot, MS architecture (services
should communicate through messaging)
